package com.nikolaev.JobSensei.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nikolaev.JobSensei.API.WorkWithAPI;
import com.nikolaev.JobSensei.models.Job;

@Service
public class Handler {

    // Даннный класс связывает анализатор и получения данных с помощью API

    private WorkWithAPI workWithAPI;

    public Handler(@Autowired WorkWithAPI workWithAPI) {
        this.workWithAPI = workWithAPI;
    }

    // Данный метод сначала запрашивает данные из api,
    // затем результат отправляет анализатору
    public Job analyzeProfession(String profession) {
        // ArrayNode result = workWithAPI.getProfession(profession);
        // String resultString = jsonNodeToString(result);
        // resultString = sendToAnalyzer(resultString);
        String resultString = """
                {"avg_salary":25686.830601092897,"cities":{"Москва":271,"Санкт-Петербург":67,"Нижний Новгород":19,"Екатеринбург":13,"Казань":12,"Новосибирск":6,"Ростов-на-Дону":5,"Астана":5,"Бишкек":4,"Минск":4,"Ташкент":4,"Краснодар":4,"Челябинск":4,"Самара":4,"Комсомольск-на-Амуре":4,"Сочи":3,"Алматы":3,"Иркутск":3,"Тольятти":3,"Воронеж":3,"Армения":3,"Майкоп":3,"Армавир":3,"Новочеркасск":3,"Тбилиси":3,"Таганрог":3,"Курск":3,"Барнаул":3,"Пятигорск":3,"Дзержинск (Нижегородская область)":3,"Тюмень":3,"Шахты":3,"Владивосток":3,"Петрозаводск":3,"Астрахань":3,"Иннополис":3,"Сургут":3,"Ярославль":3,"Ош":3,"Улан-Удэ":3,"Уссурийск":3,"Сыктывкар":3,"Наманган":3,"Баку":3,"Андижан":3,"Липецк":2,"Ижевск":2,"Пермь":2,"Сызрань":2,"Великий Новгород":2,"Мурманск":2,"Волгоград":2,"Самарканд":2,"Кипр":2,"Атырау":2,"Полоцк":1,"Симферополь":1,"Красноярск":1,"Владикавказ":1,"Владимир":1,"Кемерово":1,"Калуга":1,"Подольск (Московская область)":1,"Ульяновск":1,"Московский":1,"Климовск (Московская область)":1,"Химки":1,"ОАЭ":1},"skills":{"go":203,"golang":199,"postgresql":145,"sql":85,"docker":84,"linux":72,"kubernetes":70,"git":67,"kafka":64,"it":64,"redis":42,"mysql":28,"rabbitmq":27,"api":27,"rest":27,"python":26,"clickhouse":26,"торговая площадка":25,"ооп":24,"grpc":22,"базы данных":22,"gitlab":20,"php":19,"разработка логистики":17,"microservices":14,"backend":14,"tcp/ip":14,"github":14,"работа в команде":12,"mongodb":12,"финансы":12,"удаленная работа":11,"ansible":9,"управление командой":9,"rest api":8,"nosql":8,"elasticsearch":8,"ci/cd":8,"работа с базами данных":8,"ит":8,"ms sql":7,"unit testing":6,"javascript":6,"английский язык":6,"aws":6,"symfony":6,"http":6,"микросервисная архитектура":6,"qa":6,"graphql":5,"c++":5,"c/c++":5,"prometheus":5,"разработка поисковых технологий":5,"grafana":4,"atlassian jira":4,"unix":4,"restful api":4,"node.js":4,"java":4,"микросервисы":4,"разработка платформы":4,"agile project management":4,"навыки продаж":3,"коммуникабельность":3,"пользователь пк":3,"azure":3,"gitlab ci":3,"css":3,"k8s":3,"jira":3,"vuejs":3,"electron":3,"gin":3,"echo":3,"оптимизация кода":3,"руководство командой разработчиков":3,"тестирование":3,"apache kafka":3,"elk":3,"leadership skills":3,"ci":3,"знание устройства автомобиля":3,"водительское удостоверение категории b":3,"организаторские навыки":2,"навыки межличностного общения":2,"телефонные переговоры":2,"поиск информации в интернет":2,"обучение и развитие":2,"грамотная речь":2,"развитие продаж":2,"swagger":2,"laravel":2,"json api":2,"nginx":2,"html":2,"субд":2,"деловое общение":2,"функциональное тестирование":2,"автоматизированное тестирование":2,"openapi":2,"revel":2,"google cloud platform":2,"bash":2,"postgres":2,"consumer goods":2,"test case":2,"внутренние сервисы":2,"devops":2,"flash mysql":2,"solid":2,"rabbit":2,"высоконагруженные системы":2,"постановка задач разработчикам":2,"elasticksearch":2,"solr":2,"ml":1,"ds":1,"управление временем":1,"контроль исполнения решений":1,"подбор персонала":1,"ориентация на клиента":1,"начало карьеры":1,"желание обучаться":1,"умение принимать решения":1,"мобильность":1,"аналитическое мышление":1,"быстрое обучение":1,"достижение поставленных целей":1,"самостоятельная работа":1,"умение работать в условиях многозадачности":1,"js":1,"ms access":1,"beta-тестирования":1,"raster arts":1,"менеджер кофейни":1,"управляющий кофейни":1,"работа с текущей базой клиентов":1,"навыки переговоров":1,"perl":1,"mysql gui tools":1,"ssh":1,"консультирование":1,"разработка по":1,"серверное программирование":1,"junior":1,"highload":1,"nats":1,"sass":1,"loki":1,"коррекция бровей":1,"окрашивание бровей":1,"ламинирование ресниц":1,"ламинирование бровей":1,"окрашивание ресниц":1,"макияж":1,"эпиляция":1,"оперативный поиск информации в сети интернет":1,"memcached":1,"теория тестирования":1,"ответственность":1,"пунктуальность":1,"вежливость":1,"scrum":1,"bitbucket":1,"разработка продукта":1,"ручное тестирование":1,"yii2":1,"openswooly":1,"nats.io":1,"mq":1,"api http":1,"мотивация персонала":1,"управленческая отчетность":1,"коммуникативные навыки":1,"django framework":1,"flask framework":1,"websocket":1,"linux/unix":1,"communication skills":1,"negotiation skills":1,"ms excel":1,"business english":1,"ms office":1,"ms powerpoint":1,"kanban":1,"кассовые операции":1,"кассовые документы":1,"барное ремесло":1,"обслуживание покупателей":1,"ruby on rails":1,"ruby":1,"boost":1,"stl":1,"управление разработкой":1,"databases":1,"nlp":1,"middle backend":1,"senior backend":1,"tarantool":1,"проведение презентаций":1,"деловая коммуникация":1,"платформа":1,"sqlboiler":1,"gorm":1,"gomock":1,"redpanda":1,"teamlead":1,"api (rest/grpc)":1,"docker/kubernetes":1,"базовые знания sql":1,"restful":1,"json":1,"html5":1,"react":1,"nest.js":1,"nodejs":1,"геосервисы":1,"c#":1,"алгоритмы и структуры данных":1,"алгоритмы":1,"msa":1,"желание учиться":1,"с++":1,"rust":1,"системы контроля версий":1,"управление проектами":1,"statistics":1,"gamedev":1,"game programming":1,"istio":1,"язык программирования go":1,"mssql":1,"s3":1,"fastapi":1,"общение с людьми":1,"ios":1,"usb":1,"торговое оборудование":1,"kibana":1,"sentry":1,"jaeger":1},"experiences":{"between3And6":275,"noExperience":134,"between1And3":111,"moreThan6":29},"skills_by_experience":{"noExperience":{"работа в команде":6,"пользователь пк":3,"знание устройства автомобиля":3,"водительское удостоверение категории b":3,"go":2,"mysql":2,"golang":2,"git":2,"sql":2,"обучение и развитие":2},"between1And3":{"go":43,"golang":32,"postgresql":31,"git":19,"linux":16,"clickhouse":16,"sql":16,"docker":15,"redis":15,"kafka":14},"between3And6":{"golang":150,"go":148,"postgresql":105,"docker":63,"sql":61,"it":51,"linux":50,"kubernetes":47,"kafka":45,"git":42},"moreThan6":{"golang":15,"go":10,"kubernetes":9,"postgresql":8,"sql":6,"redis":6,"управление командой":5,"kafka":5,"docker":5,"linux":5}},"unique_skills_by_experience":{"noExperience":["желание обучаться","обучение и развитие","развитие продаж","водительское удостоверение категории b","начало карьеры","английский язык","телефонные переговоры","грамотная речь","пользователь пк","знание устройства автомобиля","умение работать в условиях многозадачности","достижение поставленных целей","аналитическое мышление","умение принимать решения","мобильность","работа с текущей базой клиентов","навыки переговоров","быстрое обучение","коммуникабельность","навыки продаж","самостоятельная работа","rest api"],"between1And3":["работа с базами данных","ms sql","удаленная работа","javascript","ansible"],"between3And6":["microservices","mongodb","финансы","github"],"moreThan6":["kanban","restful api","http","openapi","echo","flash mysql","aws","node.js","elasticsearch","ит","agile project management","gin","управление командой"]}}
                """;
        JsonNode jsonNode = stringToJsonNode(resultString);
        return newJob(jsonNode, profession);
    }

    private Job newJob(JsonNode jsonNode, String profession) {
        Job job = new Job();
        Double avgSalary = jsonNode.get("avg_salary").asDouble();
        job.setAverageSalary(avgSalary);
        job.setCities(jsonNode.get("cities"));
        job.setSkills(jsonNode.get("skills"));
        job.setSkillsByExperience(jsonNode.get("skills_by_experience"));
        job.setExperiences(jsonNode.get("experiences"));
        job.setUniqueSkillsByExperience(jsonNode.get("unique_skills_by_experience"));
        job.setProfession(profession);
        return job;
    }

    // Отправляет json анализатору
    private String sendToAnalyzer(String json) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = "http://localhost:8000";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
                return response.body();

        } catch (URISyntaxException | IOException | InterruptedException exc) {
            System.out.println("Module - Handler, method - sendToAnalyzer");
            System.out.println(exc.getClass());
        }
        return null;
    }

    private String jsonNodeToString(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonNode stringToJsonNode(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(json);
            return root;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
