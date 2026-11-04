#!/usr/bin/env python3
"""Export exact GPT response Java for the common cohort, without modification."""
import pathlib,json,hashlib,collections,sys,os
from pathlib import PurePosixPath
root=pathlib.Path(__file__).resolve().parents[1];base=root/'generated/execution/azure-gpt-paired-20261103-v2';cohort=set((root/'generated/analysis/comparacao-tres-modelos-20261104/coorte-3232.txt').read_text().splitlines());assert len(cohort)==3232
for model,files in [('nano',sorted((root/'sast2026-artifact/responses').glob('*.jsonl'))),('mini',sorted((root/'generated/experiments/jdk-variantes/jdk-v2/respostas').glob('*/responses_openai_batch_generation.jsonl')))]:
 out=base/model
 if out.exists():raise SystemExit('Refusing to overwrite '+str(out))
 rows=[];seen=set(); failures=[]
 for f in files:
  for line in f.open():
   r=json.loads(line);parts=r['custom_id'].split('/');target='/'.join(['jdk']+parts[2:]);arm=parts[1]
   if target not in cohort:continue
   key=(target,arm)
   if key in seen:raise ValueError('duplicate '+str(key))
   seen.add(key); body=(r.get('response') or {}).get('body') or {}
   if body.get('status')!='completed':failures.append((key,'not completed'));continue
   try:
    text='\n'.join(c['text'] for msg in body['output'] if msg.get('type')=='message' for c in msg.get('content',[]) if c.get('type')=='output_text')
    obj,_=json.JSONDecoder().raw_decode(text[text.index('{'):]);fs=obj['files'];assert isinstance(fs,list) and fs and isinstance(fs[0],dict) and sum(isinstance(x,dict) for x in fs)==1
    item=fs[0];original_rel=item['relative_path'];rel=original_rel if original_rel.startswith('test/jdk/') else 'test/jdk/witup/generated/'+original_rel;q=PurePosixPath(rel);assert not q.is_absolute() and '..' not in q.parts and q.suffix=='.java' and str(q).startswith('test/jdk/')
    content=item['content'].encode();assert content.strip();pair_id=hashlib.sha256('/'.join(parts[2:]).encode()).hexdigest()[:20];path=pathlib.Path('generated-tests')/arm/pair_id/rel
    dest=out/path;dest.parent.mkdir(parents=True,exist_ok=True);dest.write_bytes(content)
    rows.append({'model':body['model'],'arm':arm,'pair_id':pair_id,'custom_id':r['custom_id'],'relative_path':rel,'exported_path':str(path),'sha256':hashlib.sha256(content).hexdigest(),'original_relative_path':original_rel,'malformed_files_array':len(fs)!=1})
   except Exception as e:failures.append((key,str(e)))
 print(model,'seen',len(seen),'exported',len(rows),'failures',len(failures),flush=True)
 if failures or len(rows)!=6464 or len(seen)!=6464:
  (out/'failures.json').write_text(json.dumps(failures,indent=2));continue
 rows.sort(key=lambda x:(x['pair_id'],x['arm']));assert len({x['pair_id'] for x in rows})==3232
 batches=out/'batches'; batches.mkdir()
 for n in range(0,len(rows),100):
  batch=batches/f'{n//100:03d}';batch.mkdir();records=rows[n:n+100]
  for row in records:
   src=out/row['exported_path'];dest=batch/row['exported_path'];dest.parent.mkdir(parents=True,exist_ok=True);os.link(src,dest)
  (batch/'test-plan.json').write_text(json.dumps(records,ensure_ascii=False)+'\n')
 (out/'execution-plan.json').write_text(json.dumps({'model':model,'targets':3232,'java_files':len(rows),'batch_count':(len(rows)+99)//100,'source':'provider response, exact file content','cohort_sha256':hashlib.sha256('\n'.join(sorted(cohort)).encode()).hexdigest()},indent=2)+'\n')
