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
