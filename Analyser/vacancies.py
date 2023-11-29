from typing import List


class Vacancy:
    def __init__(self, vacancy: dict):
        self.vacancy = vacancy

    def description(self) -> str | None:
        if "description" in self.vacancy and self.vacancy["description"] is not None:
            return self.vacancy["description"]
        return None

    def lower_salary_bound(self) -> int | None:
        if "salary" in self.vacancy and self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["from"]
        return None

    def upper_salary_bound(self) -> int | None:
        if "salary" in self.vacancy and self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["to"]
        return None

    def salary_currency(self) -> str | None:
        if "salary" in self.vacancy and self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["currency"]
        return None

    def salary_gross(self) -> bool | None:
        if "salary" in self.vacancy and self.vacancy["salary"] is not None:
            return self.vacancy["salary"]["gross"]
        return None

    def city(self) -> str | None:
        if "address" in self.vacancy and self.vacancy["address"] is not None:
            return self.vacancy["address"]["city"]
        return None

    def skills(self) -> list | None:
        if "skills" in self.vacancy and self.vacancy["skills"] is not None:
            return [skill["name"].lower() for skill in self.vacancy["skills"]]
        return None

    def experience(self) -> str | None:
        if "experience" in self.vacancy and self.vacancy["experience"] is not None:
            return self.vacancy["experience"]["id"]
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

    def skills(self) -> List[list]:
        result = [vacancy.skills() for vacancy in self.vacancies]
        return result

    def experiences(self) -> List[str]:
        result = [vacancy.experience() for vacancy in self.vacancies]
        return result
