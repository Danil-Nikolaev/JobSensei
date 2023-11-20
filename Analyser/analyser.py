from typing import List, Dict
from vacancies import Vacancies


class Analyser:
    def analyse(self, vacancies: Vacancies, avg_salary=False, cities=False) -> dict:
        result = {}

        if avg_salary:
            result.update(self.avg_salary(vacancies))
        if cities:
            result.update(self.cities(vacancies))

        return result

    def avg_salary(self, vacancies: Vacancies) -> dict:
        result = {}
        result["avg_salary"] = 0
        valid_count = 0

        lower_salary_bounds = vacancies.lower_salary_bounds()
        upper_salary_bounds = vacancies.upper_salary_bounds()
        salary_currencies = vacancies.salary_currencies()
        salary_grosses = vacancies.salary_grosses()

        for i, _ in enumerate(lower_salary_bounds):
            lower_salary_bound = lower_salary_bounds[i] if lower_salary_bounds[i] is not None else 0
            upper_salary_bound = upper_salary_bounds[i] if upper_salary_bounds[i] is not None else lower_salary_bound
            result["avg_salary"] += ((lower_salary_bound + upper_salary_bound) / 2)
            valid_count += 1

        result["avg_salary"] /= valid_count
        return result

    def cities(self, vacancies: Vacancies, sort_desc=True) -> Dict[str, dict]:
        result = {}
        result["cities"] = {}

        cities = vacancies.cities()

        for city in cities:
            result["cities"][city] = (result["cities"][city] + 1) if city in result["cities"] else 1

        if sort_desc:
            result["cities"] = dict(sorted(result["cities"].items(), key=lambda item: item[1], reverse=True))

        return result
