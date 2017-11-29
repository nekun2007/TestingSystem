#!/usr/bin/env python3
# -*- coding: UTF-8 -*-

import sys

import sqlite3

from parse import parse


def file_to_list(source):
    result = list()
    question_found = False

    for line in source:
        parsed_question = parse("{num:d}. {text}", line)
        parsed_answer = parse("{num:D}) {text}", line)

        if not question_found and parsed_question is not None:
            # Обнаружен новый вопрос
            question_found = True
            current = dict()
            current['text'] = parsed_question['text'].strip()
            result.append(current)
            continue

        if question_found and parsed_answer is None:
            # Текст вопроса ещё продолжается
            current['text'] += '\n' + line.strip()
            continue

        if question_found and parsed_answer is not None:
            # Пошли варианты ответов
            answer_letter = parsed_answer['num'][0]
            current[answer_letter] = parsed_answer['text'].strip()

            if '*' in parsed_answer['num']:  # Найден правильный вариант
                current['ans'] = 'абвг'.index(parsed_answer['num'][0])+1

            if answer_letter == 'г':  # Прерываемся на варианте "г"
                question_found = False

            continue

    return result


if __name__ == "__main__":
    data_source = input("Введите путь до файла с вопросами: ")

    with open(data_source, mode="r") as source:
        result = file_to_list(source)

    print("Найдено {0} вопросов".format(len(result)))

    if input("Хотите проверить, что вообще распарсилось? [yN]: ") == 'y':
        counter = 1
        for question in result:
            print("========")
            print(str(counter) + ". " + question['text'])
            print("----")
            for answer in 'абвг':
                print(answer + ") " + question[answer])
            print("----")
            print("Ответ: " + answer)
            counter += 1

    if input("Записать в базу данных? [yN]: ") == 'y':
        db_file = input("Введите путь до файла SQlite: ")
        db_table = input("Введите название таблицы: ")
    else:
        print("А зачем тогда это всё? ¯\_(ツ)_/¯")
        sys.exit(0)

    with sqlite3.connect(db_file) as conn:
        c = conn.cursor()

        query = "DELETE FROM `{0}`"
        c.execute(query.format(db_table))

        for q in result:
            query = """
                INSERT INTO `{0}`
                (`Question`,`Ans1`,`Ans2`,`Ans3`,`Ans4`,`Right`)
                VALUES (?, ?, ?, ?, ?, ?)
                """.format(db_table)

            c.execute(query, (q['text'], q['а'], q['б'],
                              q['в'], q['г'], q['ans']))
