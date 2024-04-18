import { request } from "../../utils/userRequest.js";
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
        url: '/userAccount/register',
        method: 'post',
        data
    });
}
export function getUserid() {
    return request({
        url: '/userAccount/userid',
        method: 'get',
    });
}
export function getDetail(){
    return request({
        url: '/userAccount/detail',
        method:'get'
    })
}
export function getDetail2(userid){
    return request({
        url: `/userAccount/detail/${userid}`,
        method:'get'
    })
}