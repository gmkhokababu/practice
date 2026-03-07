import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { Task } from './task/task';
import { TaskComponent } from "./task/task.component";
import { NgForOf, NgIf } from "@angular/common";
import { MatCard } from "@angular/material/card";
import {CdkDragDrop, DragDropModule, transferArrayItem} from "@angular/cdk/drag-drop"
import {MatButtonModule} from "@angular/material/button";
import {MatDialog, MatDialogModule} from "@angular/material/dialog"
import { TaskDialogComponent, TaskDialogResult } from './task-dialog/task-dialog.component';
import {Firestore} from '@angular/fire/firestore';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    MatToolbarModule,
    MatIconModule,
    TaskComponent,
    NgForOf,
    MatCard,
    NgIf,
    DragDropModule,
    MatButtonModule,
    MatDialogModule,
  ],

  // bootstrap : [AppComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'personalAccountManager';

  constructor(private dialog:MatDialog, private store:Firestore){}
  todo : Task[]=[
    {
      title:'Bye Milk',
      description:'Go to the store and bye milk'
    },
    {
      title: 'Create a Kanban app',
      description: 'Using Firebase and Angular Crate a Kanban app'
    }
  ];

  inProgress: Task[]=[];
  done: Task[]=[];

  editTask(list: 'done'|'todo'|'inProgress', task:Task): void{
    const dialgRef = this.dialog.open(TaskDialogComponent,{
      width:'400px',
      data:{
        task,
        enableDelete:true,
      },
    });
    dialgRef.afterClosed().subscribe((result:TaskDialogResult|undefined)=>{
      if(!result){
        return;
      }
      const dataList = this[list];
      const taskIndex= dataList.indexOf(task);
      if(result.delete){
        dataList.splice(taskIndex,1);
      }else{
        dataList[taskIndex]=task;
      }
    })
  }

  drop(event: CdkDragDrop<Task[]>): void{
    if(event.previousContainer === event.container){
      return;
    }
    if(!event.container.data || !event.previousContainer.data){
      return;
    }
    transferArrayItem(
      event.previousContainer.data,
      event.container.data,
      event.previousIndex,
      event.currentIndex
    )
  }

  newTask():void{
    const dialgRef = this.dialog.open(TaskDialogComponent,{
      width: '400px',
      minHeight:'300px',
      data:{
        task:{},
      },
    });
    dialgRef
      .afterClosed()
      .subscribe((result: TaskDialogResult|undefined)=>{
        if(!result){
          return;
        }
        this.todo.push(result.task);
      });
  }

}
