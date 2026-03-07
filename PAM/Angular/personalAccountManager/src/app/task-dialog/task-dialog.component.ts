import { Component, Inject } from '@angular/core';
import { MatButton, MatFabButton } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogClose, MatDialogRef } from "@angular/material/dialog";
import { Task } from '../task/task';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms'
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-task-dialog',
  imports: [
    MatButton,
    MatDialogClose,
    MatInputModule,
    FormsModule,
    MatFabButton,
    MatIconModule
],
  templateUrl: './task-dialog.component.html',
  styleUrl: './task-dialog.component.css'
})
export class TaskDialogComponent {
  private backupTask: Partial<Task>={};

  constructor(
    public dialogRef:MatDialogRef<TaskDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TaskDialogData
  ){
    if(this.data && this.data.task){
      this.backupTask={...this.data.task};
    }
  }

  cancel():void{
    this.data.task.title=this.backupTask.title;
    this.data.task.description=this.backupTask.description;
    this.dialogRef.close(this.data);
  }

}

export interface TaskDialogData{
  task: Partial<Task>;
  enableDelete: boolean;
}

export interface TaskDialogResult{
  task: Task;
  delete?:boolean;
}
