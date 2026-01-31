import Taro from '@tarojs/taro'

const BASE_URL = 'https://api.fairfood.com'

// 封装请求方法
const request = async (options: Taro.request.Option) => {
  const { url, method = 'GET', data, header = {} } = options
  
  const token = Taro.getStorageSync('fair-food-storage')?.state?.token
  
  try {
    const res = await Taro.request({
      url: `${BASE_URL}${url}`,
      method,
      data,
      header: {
        ...header,
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      }
    })
    return res.data
  } catch (error) {
    console.error('请求失败:', error)
    throw error
  }
}

// 用户相关 API
export const userApi = {
  login: (phone: string, code: string) => 
    request({ url: '/user/login', method: 'POST', data: { phone, code } }),
  
  getProfile: () => 
    request({ url: '/user/profile' }),
  
  updateProfile: (data: any) => 
    request({ url: '/user/profile', method: 'PUT', data }),
}

// 商家相关 API
export const merchantApi = {
  getList: (params: { lat: number; lng: number; page: number; pageSize: number }) => 
    request({ url: '/merchant/list', data: params }),
  
  getDetail: (id: number) => 
    request({ url: `/merchant/${id}` }),
  
  getProducts: (merchantId: number) => 
    request({ url: `/merchant/${merchantId}/products` }),
}

// 商品相关 API
export const productApi = {
  search: (keyword: string) => 
    request({ url: '/products/search', data: { keyword } }),
}

// 购物车 API
export const cartApi = {
  getList: () => 
    request({ url: '/cart/list' }),
  
  add: (data: { productId: number; quantity: number; spec?: string }) => 
    request({ url: '/cart/add', method: 'POST', data }),
  
  update: (cartId: number, quantity: number) => 
    request({ url: `/cart/${cartId}`, method: 'PUT', data: { quantity } }),
  
  remove: (cartId: number) => 
    request({ url: `/cart/${cartId}`, method: 'DELETE' }),
  
  clear: () => 
    request({ url: '/cart/clear', method: 'POST' }),
}

// 订单相关 API
export const orderApi = {
  create: (data: any) => 
    request({ url: '/order/create', method: 'POST', data }),
  
  getList: (params: { status?: string; page: number; pageSize: number }) => 
    request({ url: '/order/list', data: params }),
  
  getDetail: (id: number) => 
    request({ url: `/order/${id}` }),
  
  pay: (orderId: number) => 
    request({ url: `/order/${orderId}/pay`, method: 'POST' }),
  
  cancel: (orderId: number, reason: string) => 
    request({ url: `/order/${orderId}/cancel`, method: 'POST', data: { reason } }),
  
  confirm: (orderId: number) => 
    request({ url: `/order/${orderId}/confirm`, method: 'POST' }),
}

// 地址相关 API
export const addressApi = {
  getList: () => 
    request({ url: '/address/list' }),
  
  getDefault: () => 
    request({ url: '/address/default' }),
  
  create: (data: any) => 
    request({ url: '/address/create', method: 'POST', data }),
  
  update: (id: number, data: any) => 
    request({ url: `/address/${id}`, method: 'PUT', data }),
  
  delete: (id: number) => 
    request({ url: `/address/${id}`, method: 'DELETE' }),
  
  setDefault: (id: number) => 
    request({ url: `/address/${id}/default`, method: 'POST' }),
}

// 骑手相关 API
export const riderApi = {
  login: (phone: string, password: string) => 
    request({ url: '/rider/login', method: 'POST', data: { phone, password } }),
  
  getOrders: () => 
    request({ url: '/rider/orders/available' }),
  
  grabOrder: (orderId: number) => 
    request({ url: `/rider/orders/${orderId}/grab`, method: 'POST' }),
  
  getMyTasks: () => 
    request({ url: '/rider/tasks' }),
  
  confirmPickup: (taskId: number) => 
    request({ url: `/rider/tasks/${taskId}/pickup`, method: 'POST' }),
  
  confirmDelivery: (taskId: number) => 
    request({ url: `/rider/tasks/${taskId}/delivery`, method: 'POST' }),
  
  getEarnings: (params: { startDate: string; endDate: string }) => 
    request({ url: '/rider/earnings', data: params }),
  
  getHistory: (params: { page: number; pageSize: number }) => 
    request({ url: '/rider/history', data: params }),
  
  withdraw: (amount: number) => 
    request({ url: '/rider/withdraw', method: 'POST', data: { amount } }),
}
