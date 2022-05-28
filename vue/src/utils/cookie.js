const cookie  = require('js-cookie')

export const set = (key,value) => {
    cookie.set(key,value)
}

export const get = (key) => {
    return cookie.get(key)
}

export const remove = (key) => {
    cookie.remove(key)
}