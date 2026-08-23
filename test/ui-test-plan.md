# Console UI test plan

## Program command

java -cp build\classes\java\main laby.Laby

## Timeout seconds

5

## Test case: Start and exit

### Aim

Verify that laby.Laby shows its welcome text and exits politely.

### Inputs

```text
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Add and list each task type

### Aim

Verify that ToDos, Deadlines, and Events are stored with their required details and displayed correctly.

### Inputs

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [T][ ] borrow book
There is a total of 1 task in your list.
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [D][ ] return book (by: Sunday)
There is a total of 2 tasks in your list.
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
There is a total of 3 tasks in your list.
____________________________________________________________

____________________________________________________________

Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Find tasks by description keyword

### Aim

Verify that `find` displays every task whose description contains the keyword, ignores keyword case, and retains the original task numbers.

### Inputs

```text
todo read book
todo write notes
deadline return book /by 2026-08-24 12:00
find BOOK
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [T][ ] read book
There is a total of 1 task in your list.
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [T][ ] write notes
There is a total of 2 tasks in your list.
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [D][ ] return book (by: Aug 24 2026 12:00)
There is a total of 3 tasks in your list.
____________________________________________________________

____________________________________________________________

Here are the matching tasks in your list:
1.[T][ ] read book
3.[D][ ] return book (by: Aug 24 2026 12:00)
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Mark and unmark a ToDo

### Aim

Verify that the completion state of a task can be changed after it is added.

### Inputs

```text
todo read book
mark 1
unmark 1
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [T][ ] read book
There is a total of 1 task in your list.
____________________________________________________________

____________________________________________________________

Understood. laby.Laby has marked the task as done.
  [T][X] read book
____________________________________________________________

____________________________________________________________

Understood. laby.Laby has marked the task as not done.
  [T][ ] read book
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Reject commands with missing required input

### Aim

Verify that laby.Laby reports a validation error when a task description or task index is omitted.

### Inputs

```text
todo
mark
unmark
delete
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

System crashing... task description cannot be empty.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Delete a task

### Aim

Verify that laby.Laby removes the selected task, reports the remaining count, and renumbers the list.

### Inputs

```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
mark 3
delete 3
list
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [T][ ] read book
There is a total of 1 task in your list.
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [D][ ] return book (by: Sunday)
There is a total of 2 tasks in your list.
____________________________________________________________

____________________________________________________________

laby.Laby has added the task. Make sure to rest, Chief :o
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
There is a total of 3 tasks in your list.
____________________________________________________________

____________________________________________________________

Understood. laby.Laby has marked the task as done.
  [E][X] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________

____________________________________________________________

laby.Laby has deleted the task. Glad to see you resting ;)
  [E][X] project meeting (from: Mon 2pm to: 4pm)
There is a total of 2 tasks in your list.
____________________________________________________________

____________________________________________________________

Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Reject malformed deadline commands

### Aim

Verify that laby.Laby rejects deadlines with a missing description, `/by` marker, or deadline value.

### Inputs

```text
deadline
deadline buy milk
deadline /by Friday
deadline buy milk /by 
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

System crashing... task description cannot be empty.
____________________________________________________________

____________________________________________________________

System crashing... please enter a deadline with /by.
____________________________________________________________

____________________________________________________________

System crashing... task description cannot be empty.
____________________________________________________________

____________________________________________________________

System crashing... task deadline cannot be empty.
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Reject malformed event commands

### Aim

Verify that laby.Laby rejects events with missing markers or empty descriptions, starting times, and ending times.

### Inputs

```text
event
event meeting /to noon
event meeting /from morning
event /from morning /to noon
event meeting /from  /to noon
event meeting /from morning /to 
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

System crashing... task description cannot be empty.
____________________________________________________________

____________________________________________________________

System crashing... please enter a starting time with /from.
____________________________________________________________

____________________________________________________________

System crashing... please enter an ending time with /to.
____________________________________________________________

____________________________________________________________

System crashing... task description cannot be empty.
____________________________________________________________

____________________________________________________________

System crashing... task starting time cannot be empty.
____________________________________________________________

____________________________________________________________

System crashing... task ending time cannot be empty.
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Reject unknown commands and invalid task indices

### Aim

Verify that laby.Laby rejects an empty command, an unknown command, non-numeric indices, and out-of-range indices.

### Inputs

```text

blah
mark abc
mark 0
unmark 2
delete abc
delete 0
bye
```

### Expected output

```text
____________________________________________________________

#       ###   ####   #   #
#      #   #  #   #   # #
#      #####  ####     #
#      #   #  #   #    #
#####  #   #  ####     #
____________________________________________________________

Hello Chief. laby.Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

System crashing... please input the correct commands.
____________________________________________________________

____________________________________________________________

System crashing... please input the correct commands.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

System crashing... please enter a valid task index.
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```
