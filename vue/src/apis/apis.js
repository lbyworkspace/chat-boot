import http from "./http"

export default {
    login(data){
        return http.post('/user/login',data)
    },

    register(data){
        return http.post('/user/register',data)
    },

    update(data,type){
        return http.post(`/user/update/${type}`,data)
    },
}