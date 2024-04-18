import { request } from "../../utils/articleRequest.js";
export function isLike(articleid) {
    return request({
        url: `/articleLike/islike/${articleid}`,
        method: 'get',
    });
}
export function like(articleid){
    return request({
        url:`/articleLike/${articleid}`,
        method:'post',
    })
}
export function disLike(articleid){
    return request({
        url:`/articleLike/${articleid}`,
        method:'delete',
    })
}