import { request } from "../utils/request.js";
export function publishVideo(data){
    return request({
        url:'/video/publish',
        method:'post',
        data
    });
}
export function uploadVideo(formData, params) {
    return request({
        url: '/video/upload',
        method: 'post',
        data: formData,
        params,
    });
}
export function getVideo(data) {
    return request({
        url: '/video/getVideo',
        method: 'post',
        data,
        responseType:'arraybuffer',
    });
}
export function findVideoByPage(pageSize, currentPage) {
    return request({
        url: `/video/${pageSize}/${currentPage}`,
        method: 'get'
    });
}
export function likeVideo(data){
    return request({
        url:'/video/like',
        method:'post',
        data
    })
}
export function dislikeVideo(data){
    return request({
        url:'/video/dislike',
        method:'post',
        data
    })
}
export function islikeVideo(data){
    return request({
        url:'/video/islike',
        method:'post',
        data
    })
}