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

    print(type(body))
    # body = b"[{\"name\": \"nameOne\"}, {\"name\": \"nameTwo\"}]"
    # body = "[{\"skills\":[],\"name\":\"Junior Java Developer\",\"description\":\"<strong>Обязанности:</strong> <ul> <li>Разработка и администрирование корпоративной шины предприятия;</li> <li>Создание интеграционного обмена между различными информационными системами;</li> <li>Администрирование и сопровождение созданных и уже реализованных интеграционных сценариев;</li> <li>Создание с &quot;0&quot; новые точки интеграции, обеспечение их бесперебойной работы, включая разбор инцидентов и их решение.</li> </ul> <strong>Требования:</strong> <ul> <li>Высшее образование в области ИТ / техническое;</li> <li>Знания в области построения сервисно-ориентированной архитектуры предприятия;</li> <li>Опыт администрирования интеграционной шины; Опыт разработки будет преимуществом;</li> <li>Опыт разработки java;</li> <li>Знания SQL;</li> <li>Опыт реализации REST API;</li> <li>Опыт администрирования и развития стека ELK приветствуется;</li> <li>Опыт работы с системами контроля версий приветствуется;</li> <li>Опыт работы с различными форматами данных (json/xml/csv) приветствуется;</li> <li>Опыт коммерческой разработки с одним из брокеров: Kafka, Rabbit MQ или Active MQ приветствуется;</li> <li>Опыт разработки с интеграционными платформами, в т.ч. Mulesoft приветствуется.</li> </ul> <strong>Условия:</strong> <ul> <li>Официальное трудоустройство по ТК РФ;</li> <li>Формат работы офисный / гибридный / удаленный (на выбор);</li> <li>Годовой бонус по результатам работы;</li> <li>ДМС после испытательного срока.</li> </ul>\",\"salary\":null}]"
    as_json = json.loads(body)

    print(type(as_json))

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
