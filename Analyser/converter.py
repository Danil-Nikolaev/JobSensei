import json


class Converter:
    @staticmethod
    def to_object(data: bytes) -> object:
        result = json.loads(data)
        return result

    @staticmethod
    def from_object(data: object) -> bytes:
        result = json.dumps(data, ensure_ascii=False)
        return result.encode("utf-8")
