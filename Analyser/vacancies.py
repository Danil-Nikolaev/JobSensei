from typing import List


class Vacancy:
    def __init__(self, vacancy: dict):
        self.vacancy = vacancy

    def description(self) -> str | None:
        if self.vacancy["description"] is not None:
            return self.vacancy["description"]
        return None

    def lower_salary_bound(self) -> int | None:
        if self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["from"]
        return None

    def upper_salary_bound(self) -> int | None:
        if self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["to"]
        return None

    def salary_currency(self) -> str | None:
        if self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["currency"]
        return None

    def salary_gross(self) -> bool | None:
        if self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["gross"]
        return None

    def city(self) -> str | None:
        if self.vacancy["address"] is not None:
            return self.vacancy["address"]["city"]
        return None


class Vacancies:
    def __init__(self):
        self.vacancies = []

    def from_json(self, vacancies: List[dict]):
        self.vacancies = [Vacancy(vacancy) for vacancy in vacancies]
        return self

    def descriptions(self) -> List[str]:
        result = [vacancy.description() for vacancy in self.vacancies]
        return result

    def lower_salary_bounds(self) -> List[int]:
        result = [vacancy.lower_salary_bound() for vacancy in self.vacancies]
        return result

    def upper_salary_bounds(self) -> List[int]:
        result = [vacancy.upper_salary_bound() for vacancy in self.vacancies]
        return result

    def salary_currencies(self) -> List[str]:
        result = [vacancy.salary_currency() for vacancy in self.vacancies]
        return result

    def salary_grosses(self) -> List[bool]:
        result = [vacancy.salary_gross() for vacancy in self.vacancies]
        return result

    def cities(self) -> List[str]:
        result = [vacancy.city() for vacancy in self.vacancies]
        return result
