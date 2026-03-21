from __future__ import annotations

import json
import os
from dataclasses import dataclass
from typing import Any
from urllib import error
from urllib import request

from witup_llm.models import ModelConfig


@dataclass(slots=True)
class LLMResponse:
    payload: dict[str, Any]
    raw_text: str


class HttpLLMClient:
    def complete_json(
        self,
        model: ModelConfig,
        system_prompt: str,
        user_prompt: str,
    ) -> LLMResponse:
        text = self.complete_text(model, system_prompt, user_prompt)
        payload = json.loads(extract_json_payload(text))
        return LLMResponse(payload=payload, raw_text=text)

    def complete_text(
        self,
        model: ModelConfig,
        system_prompt: str,
        user_prompt: str,
    ) -> str:
        if model.provider == "ollama":
            return self._complete_ollama(model, system_prompt, user_prompt)
        if model.provider == "openai_compatible":
            return self._complete_openai_compatible(model, system_prompt, user_prompt)
        raise ValueError(f"Unsupported provider `{model.provider}` for model `{model.key}`.")

    def _complete_ollama(
        self,
        model: ModelConfig,
        system_prompt: str,
        user_prompt: str,
    ) -> str:
        prompt = (
            f"System instructions:\n{system_prompt}\n\n"
            f"User request:\n{user_prompt}\n\n"
            "Return valid JSON only."
        )
        payload = {
            "model": model.model,
            "prompt": prompt,
            "stream": False,
            "options": {"temperature": model.temperature},
        }
        response = self._post_json(
            url=f"{model.base_url.rstrip('/')}/api/generate",
            payload=payload,
            timeout_seconds=model.timeout_seconds,
        )
        text = str(response.get("response", "")).strip()
        if not text:
            raise ValueError(f"Ollama model `{model.key}` returned an empty response.")
        return text

    def _complete_openai_compatible(
        self,
        model: ModelConfig,
        system_prompt: str,
        user_prompt: str,
    ) -> str:
        headers: dict[str, str] = {}
        if model.api_key_env:
            api_key = os.getenv(model.api_key_env)
            if not api_key:
                raise ValueError(
                    f"Environment variable `{model.api_key_env}` is required for `{model.key}`."
                )
            headers["Authorization"] = f"Bearer {api_key}"

        payload = {
            "model": model.model,
            "temperature": model.temperature,
            "messages": [
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": user_prompt},
            ],
            "response_format": {"type": "json_object"},
        }
        response = self._post_json(
            url=f"{model.base_url.rstrip('/')}/chat/completions",
            payload=payload,
            timeout_seconds=model.timeout_seconds,
            headers=headers,
        )
        choices = response.get("choices", [])
        if not choices:
            raise ValueError(f"OpenAI-compatible model `{model.key}` returned no choices.")
        text = str(choices[0]["message"]["content"]).strip()
        if not text:
            raise ValueError(f"OpenAI-compatible model `{model.key}` returned empty content.")
        return text

    def _post_json(
        self,
        url: str,
        payload: dict[str, Any],
        timeout_seconds: int,
        headers: dict[str, str] | None = None,
    ) -> dict[str, Any]:
        encoded = json.dumps(payload).encode("utf-8")
        request_headers = {
            "Content-Type": "application/json",
            "Accept": "application/json",
        }
        if headers:
            request_headers.update(headers)

        req = request.Request(url=url, data=encoded, headers=request_headers, method="POST")
        try:
            with request.urlopen(req, timeout=timeout_seconds) as response:
                body = response.read().decode("utf-8")
        except error.HTTPError as exc:
            body = exc.read().decode("utf-8", errors="replace")
            raise RuntimeError(f"HTTP error {exc.code} from {url}: {body}") from exc
        except error.URLError as exc:
            raise RuntimeError(f"Failed to reach model endpoint {url}: {exc}") from exc

        try:
            return json.loads(body)
        except json.JSONDecodeError as exc:
            raise RuntimeError(f"Model endpoint {url} returned invalid JSON: {body}") from exc


def extract_json_payload(text: str) -> str:
    stripped = text.strip()
    if stripped.startswith("```"):
        stripped = strip_code_fence(stripped)

    start = find_first_json_start(stripped)
    if start is None:
        raise ValueError(f"Could not find JSON payload in model output: {text}")
    fragment = stripped[start:]
    end = find_matching_json_end(fragment)
    if end is None:
        raise ValueError(f"Could not parse JSON payload from model output: {text}")
    return fragment[: end + 1]


def strip_code_fence(text: str) -> str:
    lines = text.splitlines()
    if len(lines) >= 2 and lines[0].startswith("```") and lines[-1].startswith("```"):
        return "\n".join(lines[1:-1]).strip()
    return text


def find_first_json_start(text: str) -> int | None:
    object_pos = text.find("{")
    array_pos = text.find("[")
    candidates = [pos for pos in (object_pos, array_pos) if pos != -1]
    return min(candidates) if candidates else None


def find_matching_json_end(text: str) -> int | None:
    stack: list[str] = []
    in_string = False
    escape = False
    for index, char in enumerate(text):
        if in_string:
            if escape:
                escape = False
            elif char == "\\":
                escape = True
            elif char == '"':
                in_string = False
            continue
        if char == '"':
            in_string = True
            continue
        if char in "{[":
            stack.append("}" if char == "{" else "]")
            continue
        if char in "}]":
            if not stack or char != stack.pop():
                return None
            if not stack:
                return index
    return None

