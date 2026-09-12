# Laby User Guide

## Getting Started

1. Make sure Java 25 is installed.
2. Open the project in IntelliJ IDEA, or run the application from the project folder:

   ```powershell
   .\gradlew.bat run
   ```

3. When the Laby window opens, type a command in the input box and press `Enter` or click `Send`.
4. Laby stores your tasks and contacts in `data/laby.txt`, so your data is available the next time you start the application.

In the command formats below, replace angle-bracketed values such as `<DESCRIPTION>` with your own information. Do not type the angle brackets.

## Adding To-Dos

Use `todo` followed by a description to add a task without a date or time.

Format: `todo <DESCRIPTION>`

```text
todo read chapter 1
```

Laby adds the to-do as an incomplete task.

## Adding Deadlines

Use `deadline` followed by a description and the `/by` marker. Enter the deadline in `yyyy-MM-dd HH:mm` format.

Format: `deadline <DESCRIPTION> /by <DATE_AND_TIME>`

```text
deadline submit report /by 2026-09-20 23:59
```

Laby adds the deadline as an incomplete task.

## Adding Events

Use `event` followed by a description, a start time with `/from`, and an end time with `/to`. Enter both times in `yyyy-MM-dd HH:mm` format.

Format: `event <DESCRIPTION> /from <START_DATE_AND_TIME> /to <END_DATE_AND_TIME>`

```text
event project meeting /from 2026-09-21 14:00 /to 2026-09-21 16:00
```

Laby adds the event as an incomplete task.

## Viewing Tasks and Contacts

Use `list` to display all tasks and contacts. Tasks and contacts are numbered separately.

Format: `list`

```text
list
```

## Finding Tasks

Use `find` followed by a keyword to display tasks whose descriptions contain that keyword. The search is not case-sensitive.

Format: `find <KEYWORD>`

```text
find report
```

## Marking Tasks as Done or Not Done

Use the task's number from the task list with `mark` or `unmark`.

Formats:

- `mark <TASK_INDEX>`
- `unmark <TASK_INDEX>`

The task index is the number shown beside the task in the task list. For example, `mark 1` marks the first task as done.

```text
mark 1
unmark 1
```

## Deleting Tasks

Use `deletetask` followed by the task's number.

Format: `deletetask <TASK_INDEX>`

```text
deletetask 2
```

Task numbers are updated after a task is deleted.

## Adding Contacts

Use `contact` followed by a name, phone number with `/p`, and email address with `/e`.

Format: `contact <NAME> /p <PHONE_NUMBER> /e <EMAIL_ADDRESS>`

```text
contact Jane Doe /p 91234567 /e jane@example.com
```

## Deleting Contacts

Use `deletecontact` followed by the contact's number from the contact list.

Format: `deletecontact <CONTACT_INDEX>`

```text
deletecontact 1
```

## Exiting Laby

Use `bye` to close the application.

Format: `bye`

```text
bye
```

## Command Summary

- `todo <DESCRIPTION>`
- `deadline <DESCRIPTION> /by <DATE_AND_TIME>`
- `event <DESCRIPTION> /from <START_DATE_AND_TIME> /to <END_DATE_AND_TIME>`
- `list`
- `find <KEYWORD>`
- `mark <TASK_INDEX>`
- `unmark <TASK_INDEX>`
- `deletetask <TASK_INDEX>`
- `contact <NAME> /p <PHONE_NUMBER> /e <EMAIL_ADDRESS>`
- `deletecontact <CONTACT_INDEX>`
- `bye`
