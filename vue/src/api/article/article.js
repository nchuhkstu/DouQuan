import { request } from "../../utils/articleRequest.js";
export function publish(data) {
    return request({
        url: '/article',
        method: 'post',
        data
    });
}
export function getHot(pageSize,currentPage){
    return request({
        url:`/article/${pageSize}/${currentPage}`,
        method:'get',
    })
}