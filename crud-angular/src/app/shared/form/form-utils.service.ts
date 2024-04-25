import {
  UntypedFormArray,
  UntypedFormControl,
  UntypedFormGroup,
} from '@angular/forms';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FormUtilsService {
  constructor() {}

  /**
   * Marca todos os campos de um grupo de formulário ou array de formulário como touched.
   * @param formGroup O grupo de formulário ou array de formulário a ser validado.
   */
  validateAllFormFields(formGroup: UntypedFormGroup | UntypedFormArray) {
    // Itera sobre todos os controles do grupo de formulário
    Object.keys(formGroup.controls).forEach((field) => {
      // Obtém o controle atual
      const control = formGroup.get(field);
      // Verifica se o controle é um controle de formulário
      if (control instanceof UntypedFormControl) {
        // Marca o controle como touched
        control.markAsTouched({ onlySelf: true });
      }
      // Verifica se o controle é um UntypedFormGroup ou UntypedFormArray
      else if (
        control instanceof UntypedFormGroup ||
        control instanceof UntypedFormArray
      ) {
        // Marca o controle como touched
        control.markAsTouched({ onlySelf: true });
        // Chama recursivamente o método para validar todos os campos desse controle
        this.validateAllFormFields(control);
      }
    });
  }

  /**
   * Recupera a mensagem de erro para um campo específico dentro de um grupo de formulário.
   * @param formGroup O grupo de formulário contendo o campo.
   * @param fieldName O nome do campo para o qual recuperar a mensagem de erro.
   * @returns A mensagem de erro para o campo especificado.
   */
  getErrorMessage(formGroup: UntypedFormGroup, fieldName: string) {
    const field = formGroup.get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  /**
   * Recupera a mensagem de erro para um determinado campo de controle de formulário.
   * @param field O campo de controle de formulário para o qual recuperar a mensagem de erro.
   * @returns A mensagem de erro para o campo especificado.
   */
  private getErrorMessageFromField(field: UntypedFormControl) {
    if (field?.hasError('required')) {
      return 'Campo obrigatório!';
    }
    if (field?.hasError('minlength')) {
      const requiredLength: number = field.errors
        ? field.errors['minlength']['requiredLength']
        : 5;
      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres.`;
    }
    if (field?.hasError('maxlength')) {
      const requiredLength: number = field.errors
        ? field.errors['maxlength']['requiredLength']
        : 100;
      return `Tamanho máximo precisa ser de ${requiredLength} caracteres.`;
    }

    return 'Campo inválido';
  }

  /**
   * Recupera a mensagem de erro para um campo específico dentro de um array de formulário.
   * @param formGroup O grupo de formulário contendo o array de formulário.
   * @param formArrayName O nome do array de formulário.
   * @param fieldName O nome do campo dentro do array de formulário.
   * @param index O índice do array de formulário para o qual recuperar a mensagem de erro.
   * @returns A mensagem de erro para o campo especificado dentro do array de formulário.
   */
  getFormArrayFieldErrorMessage(
    formGroup: UntypedFormGroup,
    formArrayName: string,
    fieldName: string,
    index: number
  ) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    const field = formArray.controls[index].get(
      fieldName
    ) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  /**
   * Verifica se um array de formulário é obrigatório e não foi preenchido.
   * @param formGroup O grupo de formulário contendo o array de formulário.
   * @param formArrayName O nome do array de formulário.
   * @returns Um valor booleano indicando se o array de formulário é obrigatório e não foi preenchido.
   */
  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string) {
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return (
      !formArray.valid && formArray.hasError('required') && formArray.touched
    );
  }
}
