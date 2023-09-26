from typing import List, Dict, Union
import requests
import time
import json

def getVacancies(profession: str) -> List[Dict]:
    "Делает запрос к HH API и записывает информацию о вакансиях в лог. Возвращает список со всеми вакансиями"
    result: List[Dict] = []
    vacancies_log = open("vacancies_log.txt", "w", encoding="utf-8")

    # Запросим вакансии у HH API
    for i in range(20):
        request: str = f"https://api.hh.ru/vacancies?text={profession}&search_field=name&per_page=100&page={i}"
        response = requests.get(request)
        vacancies_json: Dict = response.json()
        for vacancy in vacancies_json["items"]:
            result.append(vacancy)

    # Выведем ответ в файл
    vacancies_log.write(json.dumps(result, ensure_ascii=False, indent=4))

    return result


def detailVacancies(vacancies: List[Dict]):
    "Добавляет описание и скиллы к каждой вакансии и записывает информацию в лог. Ничего не возвращает"
    vacancies_log = open("detailed_vacancies_log.txt", "w", encoding="utf-8")
    count = 0
    vacancies_size = len(vacancies)

    while vacancies:
        vacancy = vacancies.pop(0)
        vacancy_id: str = vacancy["id"]
        request: str = f"https://api.hh.ru/vacancies/{vacancy_id}"
        response = requests.get(request)
        vacancy_detailed = response.json()
        vacancies_log.write(str(count))
        vacancies_log.write('\n')
        vacancies_log.write(json.dumps(vacancy_detailed, ensure_ascii=False, indent=4))
        vacancies_log.write('\n')
        # Берём ["description"], ["key_skills"]
        try:
            vacancy["description"] = vacancy_detailed["description"]
            vacancy["key_skills"] = vacancy_detailed["key_skills"]
        except KeyError:
            print(f"Исключение на {count}")
            vacancies.append(vacancy)
        count += 1
        count = count % vacancies_size

    # Выведем ответ в файл
    # vacancies_log.write(json.dumps(vacancies, ensure_ascii=False, indent=4))


def salaryFrom(vacancy: Dict) -> Union[float, None]:
    "Возвращает нижний порог зарплаты, если в вакансии есть информация о зарплате"
    if (vacancy["salary"] is not None):
        return vacancy["salary"]["from"]


def salaryTo(vacancy: Dict) -> Union[float, None]:
    "Возвращает верхний порог зарплаты, если в вакансии есть информация о зарплате"
    if (vacancy["salary"] is not None):
        return vacancy["salary"]["to"]


def salaryCurrency(vacancy: Dict) -> Union[str, None]:
    "Возвращает валюту, если в вакансии есть информация о зарплате"
    if (vacancy["salary"] is not None):
        return vacancy["salary"]["currency"]


def salaryGross(vacancy: Dict) -> Union[bool, None]:
    "Возвращает, является ли зарплата гросс, если в вакансии есть информация о зарплате"
    if (vacancy["salary"] is not None):
        return vacancy["salary"]["gross"]


def averageSalary(vacancies: List[Dict]) -> float:
    "Возвращает среднюю зарплату из списка вакансий (пока учитываются только RUR)"
    result = 0
    valid_count = 0

    for vacancy in vacancies:
        salary_currency = salaryCurrency(vacancy)
        if (salary_currency == "RUR"):
            print(vacancy["salary"])
            salary_from = salaryFrom(vacancy) if (salaryFrom(vacancy) is not None) else 0
            salary_to = salaryTo(vacancy) if (salaryTo(vacancy) is not None) else salary_from
            result += ((salary_from + salary_to) / 2)
            print(((salary_from + salary_to) / 2))
            valid_count += 1

    result /= valid_count
    print(valid_count)
    return result


def main() -> int:
    vacancies: List[Dict] = getVacancies("Повар")
    # detailVacancies(vacancies)
    print(averageSalary(vacancies))
    return 0


main()
