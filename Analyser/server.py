from PIL import Image, ImageFile
import pprint
import io
import json

async def app(scope, receive, send):
    assert scope['type'] == 'http'

    body = b''

    more_body = True
    while more_body:
        event = await receive()
        body += event['body']
        more_body = event['more_body']

    # print(len(binary_image))

    # ImageFile.LOAD_TRUNCATED_IMAGES = False

    # stream = io.BytesIO(binary_image)

    # image = Image.open(stream)

    # image.show()

    as_json: json = json.loads(body)

    print(as_json)

    await send({
        'type': 'http.response.start',
        'status': 200,
        'headers': [
            [b'content-type', b'text/plain'],
        ],
    })

    await send({
        'type': 'http.response.body',
        'body': b'Hello, world!',
    })