import { Course } from './../../model/course';
import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  UntypedFormArray,
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { AppMaterialModule } from '../../../shared/app-material/app-material.module';
import { SharedModule } from '../../../shared/shared.module';
import { CoursesService } from '../../services/courses.service';
import { Lesson } from '../../model/lesson';
import { FormUtilsService } from '../../../shared/form/form-utils.service';

@Component({
  selector: 'app-course-form',
  standalone: true,
  imports: [AppMaterialModule, SharedModule, ReactiveFormsModule, CommonModule],
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss',
})
export class CourseFormComponent implements OnInit {
  // '!' permite criar a variável sem ter que inicializá-la no momento.
  form!: FormGroup;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute,
    public formUtils: FormUtilsService
  ) {}

  ngOnInit(): void {
    // Preenche o formulário com os dados do curso, se houver
    const course: Course = this.route.snapshot.data['course'];

    this.form = this.formBuilder.group({
      _id: [course._id],
      name: [
        course.name,
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      category: [course.category, [Validators.required]],
      lessons: this.formBuilder.array(this.retriveLessons(course), Validators.required)
    });
  }

  // Método para recuperar as lições de um curso e preparar os formulários correspondentes
  private retriveLessons(course: Course) {
    const lessons = [];
    if (course?.lessons) {
      // Se o curso tiver lições, percorre cada uma delas para criar os formulários correspondentes
      course.lessons.forEach((lesson) =>
        lessons.push(this.createLesson(lesson))
      );
    } else {
      // Se o curso não tiver lições, cria um formulário vazio para uma lição
      lessons.push(this.createLesson);
    }

    return lessons;
  }

  // Método para criar um formulário de lição
  // Recebe uma lição como parâmetro (pode ser vazio)
  private createLesson(lesson: Lesson = { id: '', name: '', youtubeUrl: '' }) {
    // Cria um formulário para a lição com os campos de id, nome e URL do YouTube
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name,
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      youtubeUrl: [lesson.youtubeUrl,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
        ],
      ],
    });
  }

  getLessonsFormArray(){
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  onSubmit() {
    if(this.form.valid){
      this.service.save(this.form.value).subscribe(
        (result) => this.onSuccess(),
        (error) => this.onError() // Use this.onError() em vez de onError()
      );
    } else {
      this.formUtils.validateAllFormFields(this.form);
    }
  }

  addNewLesson(){
    const lessons = this.form.get('lessons') as UntypedFormArray;
    // quando createLesson é chamado sem argumentos já cria uma lesson vazia pr default
    lessons.push(this.createLesson());
  }

  removeLesson(index: number) {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }

  onCancel() {
    this.location.back();
  }

  private onSuccess() {
    this.snackBar.open('Curso salvo com sucesso!', '', { duration: 3000 });
    this.location.back();
  }

  private onError() {
    this.snackBar.open('Erro ao salvar curso', '', { duration: 3000 });
  }

}
