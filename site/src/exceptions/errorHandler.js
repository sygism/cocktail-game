import { pushError } from './errorQueue';


export function handleError(error) {
  console.log(error.code.toString());
  pushError(error.code.toString());
}
