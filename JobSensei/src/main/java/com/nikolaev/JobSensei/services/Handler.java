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
        ArrayNode result = workWithAPI.getProfession(profession);
        String resultString = jsonNodeToString(result);
        // String resultString = "[{\"name\":\"Team Lead Golang / Руководитель группы разработки на Go (TATLIN.UNIFIED)\",\"description\":\"<p>R&amp;D центры YADRO находятся в Москве, Санкт-Петербурге, Нижнем Новгороде и Минске, производственная площадка расположена в Московской области и строится завод полного цикла. На текущий момент нас уже более 3000 человек, и мы продолжаем активно расти и расширять команду.</p> <p>Департамент разработки подсистем управления - часть большой команды разработки флагманского продукта компании YADRO – TATLIN.UNIFIED. Продукт представляет собой современное хранилище, созданное для решения задач традиционных корпоративных приложений, больших данных и аналитики, которое используют в ЦОД, публичных и частных облаках.</p> <p>Мы создаем подсистему управления СХД – это высоконагруженное приложение, состоящее из 2 десятков сервисов, написанных на Go. Сервисы взаимодействуют между собой, общаются к подсистемам управления данными, а также управляют аппаратными компонентами СХД.</p> <p>Уделяем большое внимание правильной реализации задач: следим чтобы все работало быстро и с оптимальным потреблением аппаратных ресурсов. Каждая задача требует хорошей проработки и глубокого осмысления.</p> <p>Мы разрабатываем на Go, но для нас нет так важны глубокие знания этого языка, намного важнее базовая подготовка по инженерным технологиям: знание и понимание алгоритмов обработки данных, основных структур работы с данными, и, конечно, желание развиваться, решать задачи, осмысливая их.</p> <p> </p> <p><em><strong>Чем предстоит заниматься</strong></em></p> <ul> <li>Проектированием и разработкой управляющей подсистемы высокопроизводительной системы хранения данных (продукт класса Storage Area Network, Network Attached Storage, основной язык Golang);</li> <li>руководством командой разработки (5 разработчиков и 2 QA);</li> <li>анализом, декомпозицией задач на разработку, проведением ревью решений и кода;</li> <li>проработкой функциональных спецификаций, формированием и согласованием технической документации;</li> <li>созданием архитектурных решений для развития текущих и новых компонент системы;</li> <li>взаимодействием со смежными подразделениями для координации работ и устранения препятствий в работе команды;</li> <li>техническим лидерством; наставничеством для младших разработчиков.</li> </ul> <p> </p> <p><em><strong>Мы ожидаем от будущего члена команды</strong></em></p> <ul> <li>Опыт работы руководителем группы или архитектором от 3 лет;</li> <li>опыт разработки высоконагруженных систем на одном из языков: Go, Java, C/C++, C#, Python (опыт разработки на Go не является обязательным);</li> <li>понимание принципов проектирования отказоустойчивых систем, знание основных паттернов проектирования;</li> <li>уверенное понимание принципов работы ОС Linux;</li> <li>широкий технический бэкграунд;</li> <li>знание принципов построения сетей передачи данных; основных телекоммуникационных протоколов; принципов работы и особенностей телекоммуникационного оборудования;</li> <li>знание английского языка на уровне чтения технической документации;</li> <li>высшее образование.</li> </ul> <p> </p> <p><em><strong>Будем рады предложить Вам</strong></em></p> <ul> <li>Программу поддержки инноваций: премии за научные достижения, публикацию статей, выступления на конференциях и регистрацию патентов;</li> <li>обучение и развитие: учебный портал с курсами и лекциями от внешних и внутренних экспертов, дополнительное профессиональное обучение, изучение английского, участие в конференциях;</li> <li>лекторий с выдающимися экспертами: инженерами, учеными и исследователями;</li> <li>заботу о здоровье: ДМС с первых дней работы, льготные условия страхования близких;</li> <li>поддержку в личных вопросах: консультации юристов, психологов, экспертов по ЗОЖ и управлению финансами;</li> <li>открытое общение: регулярные онлайн-встречи всей команды YADRO.</li> </ul>\",\"salary\":{\"from\":null,\"to\":400000,\"currency\":\"RUR\",\"gross\":false},\"skills\":[{\"name\":\"Golang\"},{\"name\":\"Git\"}]}]";
        resultString = sendToAnalyzer(resultString);
        System.out.println(resultString);
        return new Job();
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
            System.out.println("Module - HHAPI, method - getJson with one parametr");
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

}
