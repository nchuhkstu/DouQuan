import { request } from "../../utils/userRequest.js";
export function getUserArticle(userid,pageSize,currentPage) {
    return request({
        url: `/userArticle/${userid}/${pageSize}/${currentPage}`,
        method: 'get',
    });
}
export function deleteUserArticle(data) {
    return request({
        url: '/userArticle',
        method: 'delete',
        data:data,
    });
}