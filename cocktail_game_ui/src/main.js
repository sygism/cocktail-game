import './assets/main.css'

import { createApp } from 'vue'

import 'bootstrap/dist/css/bootstrap.css'
import * as bootstrap from 'bootstrap/dist/js/bootstrap.bundle';
import GameContainer from '@/components/GameContainer.vue'
import ErrorContainer from '@/components/ErrorContainer.vue'

createApp(ErrorContainer).provide('bootstrap', bootstrap).mount('#errors');
createApp(GameContainer).provide('bootstrap', bootstrap).mount('#game');
