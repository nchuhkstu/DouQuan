import { request } from "../utils/userRequest.js";
export function login(data) {
    return request({
        url: '/userAccount/login',
        method: 'post',
        data
    });
}
export function islogin() {
    return request({
        url: '/userAccount',
        method: 'get',
    });
}
export function regist(data) {
    return request({
        url: '/userAccount/regist',
        method: 'post',
        data
    });
}
export function getDetailByUserid(data){
    return request({
        url:'/user/useridDetail',
        method:'post',
        data
    })
}
export function getDetailByToken(data){
    return request({
        url:'/user/tokenDetail',
        method:'post',
        data
    })
}
export function istest() {
    return request({
        url: '/user/istest',
        method: 'get',
    });
}
export function passtest() {
    return request({
        url: '/user/passtest',
        method: 'get',
    });
}