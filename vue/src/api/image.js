import { request } from "../utils/request.js";
export function uploadAvatar(formData, params) {
    return request({
        url: '/image/avatar',
        method: 'post',
        data: formData,
        params: params,
    });
}
export function getAvatar(userid) {
    return request({
        url: '/image/getAvatar',
        method: 'post',
        data:userid,
        responseType:'arraybuffer',
    });
}
export function uploadPhoto(formData, params) {
    return request({
        url: '/image/photo',
        method: 'post',
        data: formData,
        params,
    });
}
export function getPhoto(data) {
    return request({
        url: '/image/getPhoto',
        method: 'post',
        data,
        responseType:'arraybuffer',
    });
}