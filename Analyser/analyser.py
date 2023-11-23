from typing import List, Dict
import copy
from vacancies import Vacancies


class Analyser:
    def analyse(self, vacancies: Vacancies,
                avg_salary=False,
                cities=False,
                descriptions=False,
                skills=False,
                experiences=False,
                skills_by_experience=False,
                unique_skills_by_experience=False) -> dict:

        result = {}

        if descriptions:
            result.update(self.descriptions(vacancies))
        if avg_salary:
            result.update(self.avg_salary(vacancies))
        if cities:
            result.update(self.cities(vacancies))
        if skills:
            result.update(self.skills(vacancies))
        if experiences:
            result.update(self.experiences(vacancies))
        if skills_by_experience:
            result.update(self.skills_by_experience(vacancies, most_required=10))
        if unique_skills_by_experience:
            result.update(self.unique_skills_by_experience(vacancies))

        return result

    def descriptions(self, vacancies: Vacancies) -> Dict[str, list]:
        result = {}
        result["descriptions"] = vacancies.descriptions()
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
            if city is not None:
                result["cities"][city] = (result["cities"][city] + 1) if city in result["cities"] else 1

        if sort_desc:
            result["cities"] = dict(sorted(result["cities"].items(), key=lambda item: item[1], reverse=True))

        return result

    def skills(self, vacancies: Vacancies, sort_desc=True) -> Dict[str, dict]:
        result = {}
        result["skills"] = {}

        skills = vacancies.skills()

        for vacancy_skills in skills:
            if vacancy_skills is not None:
                for skill in vacancy_skills:
                    result["skills"][skill] = (result["skills"][skill] + 1) if skill in result["skills"] else 1

        if sort_desc:
            result["skills"] = dict(sorted(result["skills"].items(), key=lambda item: item[1], reverse=True))

        return result

    def experiences(self, vacancies: Vacancies, sort_desc=True) -> Dict[str, dict]:
        result = {}
        result["experiences"] = {}

        experiences = vacancies.experiences()

        for experience in experiences:
            if experience is not None:
                result["experiences"][experience] = (result["experiences"][experience] + 1) if experience in result["experiences"] else 1

        if sort_desc:
            result["experiences"] = dict(sorted(result["experiences"].items(), key=lambda item: item[1], reverse=True))

        return result

    def skills_by_experience(self, vacancies: Vacancies, most_required=0) -> Dict[str, dict]:
        result = {}
        result["skills_by_experience"] = {}
        result["skills_by_experience"]["noExperience"] = {}
        result["skills_by_experience"]["between1And3"] = {}
        result["skills_by_experience"]["between3And6"] = {}
        result["skills_by_experience"]["moreThan6"] = {}

        skills_and_experiences = zip(vacancies.skills(), vacancies.experiences())

        for skills, experience in skills_and_experiences:
            if skills is not None and experience is not None:
                for skill in skills:
                    if skill in result["skills_by_experience"][experience]:
                        result["skills_by_experience"][experience][skill] += 1
                    else:
                        result["skills_by_experience"][experience][skill] = 1

        if most_required:
            for experience in result["skills_by_experience"].keys():
                result["skills_by_experience"][experience] = dict(sorted(result["skills_by_experience"][experience].items(), key=lambda item: item[1], reverse=True))

            for experience in result["skills_by_experience"].keys():
                for i, skill in enumerate(list(result["skills_by_experience"][experience])):
                    if i >= most_required:
                        del result["skills_by_experience"][experience][skill]

        return result

    def unique_skills_by_experience(self, vacancies: Vacancies) -> Dict[str, dict]:
        result = {}
        result["unique_skills_by_experience"] = {}
        result["unique_skills_by_experience"]["noExperience"] = {}
        result["unique_skills_by_experience"]["between1And3"] = {}
        result["unique_skills_by_experience"]["between3And6"] = {}
        result["unique_skills_by_experience"]["moreThan6"] = {}

        skills_by_experience = self.skills_by_experience(vacancies, most_required=30)

        for experience in skills_by_experience["skills_by_experience"].keys():
            result["unique_skills_by_experience"][experience] = set(skills_by_experience["skills_by_experience"][experience].keys())

        buffer = copy.deepcopy(result["unique_skills_by_experience"])
        experiences = list(result["unique_skills_by_experience"])

        for i in range(0, len(experiences)):
            print(result["unique_skills_by_experience"][experiences[0]])
            print(buffer[experiences[0]])
            subtrahend = set()
            for j in range(0, len(experiences)):
                if j != i:
                    subtrahend = subtrahend | buffer[experiences[j]]
            result["unique_skills_by_experience"][experiences[i]] -= subtrahend

        for experience in result["unique_skills_by_experience"].keys():
            result["unique_skills_by_experience"][experience] = list(result["unique_skills_by_experience"][experience])

        return result
