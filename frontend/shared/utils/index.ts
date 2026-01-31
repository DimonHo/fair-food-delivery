// 工具函数集合

// 格式化价格
export const formatPrice = (price: number): string => {
  return `¥${price.toFixed(2)}`
}

// 格式化时间
export const formatTime = (date: string | Date): string => {
  const d = new Date(date)
  return `${d.getMonth() + 1}-${d.getDate()} ${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`
}

// 格式化日期
export const formatDate = (date: string | Date): string => {
  const d = new Date(date)
  return `${d.getFullYear()}-${(d.getMonth() + 1).toString().padStart(2, '0')}-${d.getDate().toString().padStart(2, '0')}`
}

// 格式化手机号
export const formatPhone = (phone: string): string => {
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

// 距离格式化（公里）
export const formatDistance = (km: number): string => {
  if (km < 1) return `${Math.round(km * 1000)}m`
  return `${km.toFixed(1)}km`
}

// 时间格式化（分钟）
export const formatDuration = (minutes: number): string => {
  if (minutes < 60) return `${Math.round(minutes)}分钟`
  const hours = Math.floor(minutes / 60)
  const mins = minutes % 60
  return `${hours}小时${mins}分钟`
}

// 验证手机号
export const isValidPhone = (phone: string): boolean => {
  return /^1\d{10}$/.test(phone)
}

// 验证验证码
export const isValidCode = (code: string): boolean => {
  return /^\d{4,6}$/.test(code)
}

// 状态文本映射
export const statusTextMap: Record<string, string> = {
  pending: '待付款',
  paid: '已付款',
  confirmed: '已接单',
  cooking: '制作中',
  ready: '待取餐',
  delivering: '配送中',
  completed: '已完成',
  cancelled: '已取消',
  refunded: '已退款'
}

// 订单状态颜色
export const statusColorMap: Record<string, string> = {
  pending: '#ff9800',
  paid: '#2196f3',
  confirmed: '#9c27b0',
  cooking: '#ff9800',
  ready: '#00bcd4',
  delivering: '#2196f3',
  completed: '#4caf50',
  cancelled: '#9e9e9e',
  refunded: '#f44336'
}

// 防抖函数
export const debounce = <T extends (...args: any[]) => any>(
  fn: T,
  delay: number
): ((...args: Parameters<T>) => void) => {
  let timer: NodeJS.Timeout | null = null
  return (...args: Parameters<T>) => {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => fn(...args), delay)
  }
}

// 节流函数
export const throttle = <T extends (...args: any[]) => any>(
  fn: T,
  interval: number
): ((...args: Parameters<T>) => void) => {
  let lastTime = 0
  return (...args: Parameters<T>) => {
    const now = Date.now()
    if (now - lastTime >= interval) {
      lastTime = now
      fn(...args)
    }
  }
}
