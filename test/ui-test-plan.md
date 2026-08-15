# Console UI test plan

## Program command

java -cp out\production\ip Laby

## Timeout seconds

5

## Test case: Start and exit

### Aim

Verify that Laby shows its welcome text and exits politely.

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

Hello Chief. Laby is your personal assistant.
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

Hello Chief. Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

Laby has added the task. Make sure to rest, Chief :o
  [T][ ] borrow book
____________________________________________________________

____________________________________________________________

Laby has added the task. Make sure to rest, Chief :o
  [D][ ] return book (by: Sunday)
____________________________________________________________

____________________________________________________________

Laby has added the task. Make sure to rest, Chief :o
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
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

Hello Chief. Laby is your personal assistant.
What orders do you have today?
____________________________________________________________

____________________________________________________________

Laby has added the task. Make sure to rest, Chief :o
  [T][ ] read book
____________________________________________________________

____________________________________________________________

Understood. Laby has marked the task as done.
  [T][X] read book
____________________________________________________________

____________________________________________________________

Understood. Laby has marked the task as not done.
  [T][ ] read book
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Reject commands with missing required input

### Aim

Verify that Laby reports a validation error when a task description or task index is omitted.

### Inputs

```text
todo
mark
unmark
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

Hello Chief. Laby is your personal assistant.
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

Goodbye. Switching to rest mode.
____________________________________________________________
```

## Test case: Reject malformed deadline commands

### Aim

Verify that Laby rejects deadlines with a missing description, `/by` marker, or deadline value.

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

Hello Chief. Laby is your personal assistant.
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

Verify that Laby rejects events with missing markers or empty descriptions, starting times, and ending times.

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

Hello Chief. Laby is your personal assistant.
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

Verify that Laby rejects an empty command, an unknown command, non-numeric indices, and out-of-range indices.

### Inputs

```text

blah
mark abc
mark 0
unmark 2
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

Hello Chief. Laby is your personal assistant.
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

Goodbye. Switching to rest mode.
____________________________________________________________
```
