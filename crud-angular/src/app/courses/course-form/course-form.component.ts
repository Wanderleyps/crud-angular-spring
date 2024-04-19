import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { AppMaterialModule } from '../../shared/app-material/app-material.module';
import { CoursesService } from '../services/courses.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-course-form',
  standalone: true,
  imports: [ AppMaterialModule, SharedModule, ReactiveFormsModule ],
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss'
})

export class CourseFormComponent implements OnInit {

  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar
  ){
    this.form = this.formBuilder.group({
      name: [null],
      category: [null]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    this.service.save(this.form.value)
      .subscribe(
        result => console.log(result),
        error => this.onError() // Use this.onError() em vez de onError()
      );
  }

  onCancel() {
    console.log('teste');
  }

  private onError() {
    this.snackBar.open('Erro ao salvar curso', '', {duration: 3000});
  }

}
