import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import apis from './apis/apis';
import '@/styles/index.scss';
import moment from 'moment';

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.$apis = apis
Vue.prototype.$TotalUser = []
Vue.prototype.$moment = moment

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
