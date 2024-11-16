
import { reactive } from 'vue';

export const errorQueue = reactive({
    errors: []
});

let id = 1;

export function pushError(error) {
    errorQueue.errors.push({ id: id++, error });
}

export function clearError(id) {
    const index = errorQueue.errors.findIndex((error) => error.id === id);
    if (index !== -1) {
        errorQueue.errors.splice(index, 1);
    }
}

export function clearAllErrors() {
    errorQueue.errors = [];
}