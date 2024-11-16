<script setup>
import {clearAllErrors, clearError, errorQueue} from "@/exceptions/errorQueue.js";
import ErrorPopup from "@/components/ErrorPopup.vue";

</script>

<template>
  <div class="errors" v-for="(error, _) in errorQueue.errors" :key="error.id">
    <ErrorPopup
        :error="error.error"
        :id="error.id"
        :callbackCloseSelf="clearError"
        :callbackCloseAll="clearAllErrors"
    />
  </div>
</template>

<style scoped>
.errors {
  display: flex;
  flex-direction: column;
  width: 75%;
  text-align: center;
  margin: auto;
}
</style>