// 共享类型定义

// 用户相关
export interface User {
  id: number
  phone: string
  nickname: string
  avatar: string
  createdAt: string
}

// 商家相关
export interface Merchant {
  id: number
  name: string
  logo: string
  description: string
  rating: number
  monthSales: number
  deliveryTime: number
  distance: number
  minPrice: number
  deliveryFee: number
  address: string
  status: 'open' | 'closed' | 'vacation'
  tags: string[]
}

export interface Product {
  id: number
  merchantId: number
  name: string
  description: string
  price: number
  originalPrice?: number
  image: string
  categoryId: number
  sales: number
  stock: number
  status: 'on' | 'off'
}

// 订单相关
export interface Order {
  id: number
  orderNo: string
  userId: number
  merchantId: number
  merchantName: string
  status: OrderStatus
  items: OrderItem[]
  totalPrice: number
  deliveryFee: number
  actualPay: number
  address: Address
  remark?: string
  createdAt: string
  paidAt?: string
  deliveredAt?: string
}

export type OrderStatus = 
  | 'pending'      // 待付款
  | 'paid'         // 已付款，待接单
  | 'confirmed'    // 已接单
  | 'cooking'      // 制作中
  | 'ready'        // 待取餐
  | 'delivering'   // 配送中
  | 'completed'    // 已完成
  | 'cancelled'    // 已取消
  | 'refunded'     // 已退款

export interface OrderItem {
  productId: number
  name: string
  price: number
  quantity: number
  spec?: string
}

export interface Address {
  id: number
  name: string
  phone: string
  province: string
  city: string
  district: string
  detail: string
  isDefault: boolean
}

// 派单相关
export interface DispatchOrder {
  id: number
  orderId: number
  riderId?: number
  status: 'pending' | 'assigned' | 'picked' | 'delivering' | 'completed'
  pickupAddress: string
  deliveryAddress: string
  estimatedTime: number
  actualTime?: number
}

// API 响应
export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

export interface PageResponse<T> {
  list: T[]
  total: number
  page: number
  pageSize: number
}

// 地理位置
export interface Location {
  latitude: number
  longitude: number
  address?: string
}
