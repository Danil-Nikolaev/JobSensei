from typing import List
import uvicorn
from converter import Converter
from vacancies import Vacancies
from analyser import Analyser


class Server:
    def __init__(self):
        self.vacancies = Vacancies()
        self.analyser = Analyser()

    async def __call__(self, scope, receive, send):
        assert scope['type'] == 'http'

        body = b''

        more_body = True
        while more_body:
            event = await receive()
            body += event['body']
            more_body = event['more_body']

        body: List[dict] = Converter.to_object(body)
        vacancies = self.vacancies.from_json(body)
        results = self.analyser.analyse(vacancies, avg_salary=True, cities=True)
        results = Converter.from_object(results)

        await send({
            'type': 'http.response.start',
            'status': 200,
            'headers': [
                [b'content-type', b'text/plain'],
            ],
        })

        await send({
            'type': 'http.response.body',
            'body': results,
        })


if __name__ == "__main__":
    server = Server()
    uvicorn.run(server)
