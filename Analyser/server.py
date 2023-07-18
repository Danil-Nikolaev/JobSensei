from PIL import Image, ImageFile
import pprint
import io

async def app(scope, receive, send):
    assert scope['type'] == 'http'

    incoming = await receive()

    body = incoming['body']

    print(scope)
    print(incoming)

    # ImageFile.LOAD_TRUNCATED_IMAGES = True

    # stream = io.BytesIO(body)

    # image = Image.open(stream)

    # image.show()

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
