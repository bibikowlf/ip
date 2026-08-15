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

## Test case: List, mark, and unmark a task

### Aim

Verify the replies and task-state changes for the `list`, `mark`, and `unmark` commands.

### Inputs

```text
read book
list
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

added: read book
____________________________________________________________

____________________________________________________________

Here are the tasks in your list:
1.[ ] read book
____________________________________________________________

____________________________________________________________

Understood. Laby has marked the task as done.
  [X] read book
____________________________________________________________

____________________________________________________________

Understood. Laby has marked the task as not done.
  [ ] read book
____________________________________________________________

____________________________________________________________

Goodbye. Switching to rest mode.
____________________________________________________________
```
