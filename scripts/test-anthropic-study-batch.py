import importlib.util
import json
from pathlib import Path
import tempfile
import unittest
from types import SimpleNamespace
from unittest.mock import patch

spec=importlib.util.spec_from_file_location('batch',Path(__file__).with_name('anthropic-study-batch.py'))
b=importlib.util.module_from_spec(spec);spec.loader.exec_module(b)

class BatchTests(unittest.TestCase):
    def test_pilot_allowance_cannot_authorize_full_run_or_overspend(self):
        with tempfile.TemporaryDirectory() as folder:
            run=Path(folder)
            b.save(run/'payload.json',{'requests':[b.native(self.row(),b.MODEL,4096)]})
            plan={'fingerprint':'fp','total':1,'protocol':{'model':b.MODEL,'max_tokens':4096},
                  'chunks':[{'payload':'payload.json','sha256':'hash'}]}
            policy={'additional_pilots':{'fp':{'run':str(run.resolve()),'payload_sha256':'hash','requests':1,'reserved_usd':'1'}},
                    'total_conservative_reservation_usd':'91.455751','operating_api_limit_usd':'100','total_cap_usd':'120'}
            self.assertIsNotNone(b.pilot_allowance(policy,run,plan))
            plan['total']=8834
            with self.assertRaises(ValueError):b.pilot_allowance(policy,run,plan)
            plan['total']=1
            policy['additional_pilots']['fp']['reserved_usd']='30'
            with self.assertRaises(ValueError):b.pilot_allowance(policy,run,plan)
            policy['additional_pilots']['fp']['reserved_usd']='0.000001'
            with self.assertRaises(ValueError):b.pilot_allowance(policy,run,plan)
    def row(self):
        return {'custom_id':'jdk/wit-context/pacote/método-42','method':'POST','url':'/v1/responses',
                'body':{'model':'gpt-4.1-nano-2025-04-14','temperature':0,'max_output_tokens':1024,
                        'input':[{'role':r,'content':[{'type':'input_text','text':t}]} for r,t in [('system','Sistema\n  sem alteração'),('user','Código\n\tç\\n')]]}}
    def message(self,stop='end_turn',text='{"files":[]}'):
        return {'id':'msg_1','model':b.MODEL,'stop_reason':stop,'content':[{'type':'text','text':text}],
                'usage':{'input_tokens':12,'output_tokens':6}}
    def test_exact_prompt_translation(self):
        row=self.row();native=b.native(row,b.MODEL)
        self.assertEqual(native['params']['system'],row['body']['input'][0]['content'][0]['text'])
        self.assertEqual(native['params']['messages'][0]['content'],row['body']['input'][1]['content'][0]['text'])
        self.assertEqual(native['params']['temperature'],0)
        self.assertEqual(native['params']['max_tokens'],1024)
        self.assertEqual(native['params']['thinking'],{'type':'disabled'})
        self.assertRegex(native['custom_id'],r'^[A-Za-z0-9_-]{1,64}$')
    def test_reorder_and_incomplete_result_validation(self):
        plan={'protocol':{'model':b.MODEL},'fingerprint':'fingerprint'}
        def line(cid):return b.encoded({'custom_id':cid,'result':{'type':'succeeded','message':self.message()}})
        result=b.convert_results(line('b')+line('a'),{'a':'original/a','b':'original/b'},plan,'batch')
        self.assertEqual([r['custom_id'] for r in result],['original/a','original/b'])
        self.assertTrue(all('response' in r for r in result))
        for raw in [line('a'),line('a')+line('a'),line('a')+line('unexpected')]:
            with self.assertRaises(ValueError):b.convert_results(raw,{'a':'a','b':'b'},plan,'batch')
    def test_haiku_preserves_text_and_omits_unsupported_sampling(self):
        row=self.row();r=b.native(row,b.HAIKU,4096)
        self.assertNotIn('temperature',r['params'])
        self.assertEqual(r['params']['thinking'],{'type':'adaptive'})
        self.assertEqual(r['params']['output_config'],{'effort':'low'})
        self.assertEqual(r['params']['max_tokens'],4096)
        self.assertEqual(r['params']['system'],row['body']['input'][0]['content'][0]['text'])
        self.assertEqual(r['params']['messages'][0]['content'],row['body']['input'][1]['content'][0]['text'])
    def test_budget_reservation_counts_full_output_and_both_input_tiers(self):
        with tempfile.TemporaryDirectory() as folder:
            run=Path(folder);r=b.native(self.row(),b.HAIKU,4096)
            b.save(run/'payload.json',{'requests':[r]})
            c={'payload':'payload.json','sha256':'hash'}
            result=b.chunk_reservation(run,{'protocol':{'model':b.HAIKU}},c)
            self.assertEqual(result['output_tokens_reserved'],4096)
            self.assertGreater(float(result['amount_usd']),4096*1.25/1e6)
            self.assertGreater(result['input_tokens_reserved'],4096)
    def test_missing_budget_blocks_haiku(self):
        with tempfile.TemporaryDirectory() as folder,patch.object(b,'ROOT',Path(folder)):
            with self.assertRaisesRegex(ValueError,'orçamento'):
                b.reserve_budget(Path(folder),{'protocol':{'model':b.HAIKU}}, {})
    def test_reservations_stop_before_exceeding_cap_and_are_idempotent(self):
        with tempfile.TemporaryDirectory() as folder:
            root=Path(folder);run=root/'run';run.mkdir()
            chunk={'number':1,'payload':'payload.json','sha256':'hash'}
            b.save(run/'payload.json',{'requests':[b.native(self.row(),b.HAIKU,4096)]})
            plan={'fingerprint':'fp','protocol':{'model':b.HAIKU}}
            allowance=b.chunk_reservation(run,plan,chunk)
            path=root/'generated/experiments/anthropic/budget-policy.json'
            policy={'status':'approved','approved_run':str(run.resolve()),'approved_plan_fingerprint':'fp',
                    'previous_pilots_cost_usd':'100','operating_api_limit_usd':'100',
                    'chunk_allowances':{'1':allowance},'reservations':{}}
            b.save(path,policy)
            with patch.object(b,'ROOT',root):
                with self.assertRaisesRegex(ValueError,'Teto'):b.reserve_budget(run,plan,chunk)
                self.assertEqual(json.loads(path.read_text())['reservations'],{})
                policy['previous_pilots_cost_usd']='0.844547';b.save(path,policy)
                b.reserve_budget(run,plan,chunk);b.reserve_budget(run,plan,chunk)
                self.assertEqual(len(json.loads(path.read_text())['reservations']),1)
    def test_output_budget_pilot_changes_only_budget(self):
        original=b.native(self.row(),b.MODEL)
        pilot=b.native(self.row(),b.MODEL,4096)
        self.assertEqual(original['custom_id'],pilot['custom_id'])
        self.assertEqual(original['params'].pop('max_tokens'),1024)
        self.assertEqual(pilot['params'].pop('max_tokens'),4096)
        self.assertEqual(original,pilot)
    def test_truncation_and_unsafe_files_remain_failures(self):
        for msg in [self.message('max_tokens'),self.message(text='{"files":[{"relative_path":"../Bad.java","content":"class Bad {}"}]}')]:
            with self.assertRaises(ValueError):b.normalize(msg)
        self.assertEqual(b.normalize(self.message())['usage']['input_tokens'],12)
    def test_submit_never_retries_uncertain_or_existing_job(self):
        for scenario in ['timeout','success']:
            with self.subTest(scenario=scenario),tempfile.TemporaryDirectory() as folder:
                run=Path(folder);(run/'payload.json').write_text('{"requests":[]}')
                plan={'fingerprint':'fp','chunks':[{'number':1,'requests':2,'payload':'payload.json'}]}
                class FakeAPI:
                    count=0
                    def call(self,*args,**kwargs):
                        self.count+=1
                        if scenario=='timeout':raise TimeoutError('timeout')
                        return {'id':'msgbatch_test1','processing_status':'in_progress'}
                api=FakeAPI()
                with patch.object(b,'ROOT',run):
                    if scenario=='timeout':
                        with self.assertRaises(TimeoutError):b.submit(run,plan,api,{1})
                        with self.assertRaises(ValueError):b.submit(run,plan,api,{1})
                        state=json.loads(b.state_path(run,1).read_text());self.assertEqual(state['status'],'uncertain')
                    else:
                        b.submit(run,plan,api,{1});b.submit(run,plan,api,{1})
                self.assertEqual(api.count,1)
    def test_budget_block_prevents_any_api_submission(self):
        with tempfile.TemporaryDirectory() as folder:
            run=Path(folder)
            b.save(run/'generated/experiments/anthropic/budget-policy.json',{'status':'blocked_pending_model_and_budget'})
            class NoAPI:
                def call(self,*a,**kw):raise AssertionError('Nenhum POST autorizado')
            with patch.object(b,'ROOT',run),self.assertRaisesRegex(ValueError,'120'):
                b.submit(run,{'fingerprint':'fp','chunks':[]},NoAPI(),{1})
    def test_no_foreign_credential_redirect(self):
        self.assertIsNone(b.NoRedirect().redirect_request(None,None,302,'Found',{},'https://other.example/'))
    def test_pilot_reuse_requires_identical_payload_and_keeps_remote_id(self):
        with tempfile.TemporaryDirectory() as folder:
            pilot=Path(folder)/'pilot';run=Path(folder)/'full';pilot.mkdir();run.mkdir()
            chunk={'number':1,'requests':1,'sha256':'same-payload','mapping_sha256':'same-map','mapping':'ids.json'}
            oldplan={'fingerprint':'pilot-fp','chunks':[chunk]}
            plan={'fingerprint':'full-fp','protocol':{'model':b.MODEL},'chunks':[dict(chunk)]}
            b.save(run/'ids.json',{'native':'canonical'})
            raw=b.encoded({'custom_id':'native','result':{'type':'succeeded','message':self.message()}})
            b.atomic(pilot/'raw/chunk-001.jsonl',raw)
            b.save(b.state_path(pilot,1),{'chunk':1,'fingerprint':'pilot-fp','batch_id':'msgbatch_test1','status':'ended','result_sha256':b.digest(raw)})
            with patch.object(b,'load_plan',return_value=oldplan):
                result=b.reuse_pilot(run,plan,pilot,{1})
                self.assertEqual(result['new_paid_requests'],0)
                self.assertEqual(b.read_lines(run/'normalized/chunk-001.jsonl')[0]['fingerprint'],'full-fp')
                self.assertEqual(json.loads(b.state_path(run,1).read_text())['batch_id'],'msgbatch_test1')
                b.reuse_pilot(run,plan,pilot,{1})  # idempotent
                plan['chunks'][0]['sha256']='different-payload'
                with self.assertRaises(ValueError):b.reuse_pilot(run,plan,pilot,{1})

if __name__=='__main__':unittest.main()
