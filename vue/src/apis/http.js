import axios from "axios";

const Axios = axios.create({
    baseURL: process.env.VUE_APP_BASEURL, // api的base_url
    withCredentials: true, // send cookies when cross-domain requests
    timeout: 30000 // 请求超时时间
})

//接口信息打印
const log = (msg,data) =>{
    if(process.env.NODE_ENV=="development"){
        console.log(msg,data)
    }
}

//请求拦截器
Axios.interceptors.request.use(
    request => {
        // request.header['accessToken'] = request.header['accessToken'] || store.state.accessToken
        return request;
    },
    err => {
        console.error(`请求拦截错误: ${err}`)
        return Promise.reject(err)
    })

//响应拦截器
Axios.interceptors.response.use(
    response => {
        log('接口' + response.config.url + '请求成功。请求返回值为', response.data)
        return Promise.resolve(response.data);
    }, err => {
        log('接口 "' + err.config.url + '" 报错，状态码为' + err.response.status + '，报错信息为', err.response.data)
        return Promise.reject(err.response.data)
    })

export default {

    get(url, params) {
        return Axios.get(url, { params: params })
    },

    post(url, data, config) {
        return Axios.post(url, data, config || {})
    },

    put(url, data, config) {
        return Axios.put(url, data, config || {})
    },

    delete(url, params) {
        return Axios.delete(url, { params: params })
    }
}