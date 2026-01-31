/**
 * 格式化价格
 */
export function formatPrice(price: number): string {
  return `¥${price.toFixed(2)}`
}

/**
 * 格式化距离
 */
export function formatDistance(distance: number): string {
  if (distance < 1) {
    return `${Math.round(distance * 1000)}m`
  }
  return `${distance.toFixed(1)}km`
}

/**
 * 格式化时间
 */
export function formatTime(date: Date | string): string {
  const d = typeof date === 'string' ? new Date(date) : date
  return `${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`
}

/**
 * 相对时间（如：3分钟前）
 */
export function relativeTime(date: Date | string): string {
  const d = typeof date === 'string' ? new Date(date) : date
  const now = new Date()
  const diff = Math.floor((now.getTime() - d.getTime()) / 1000)
  
  if (diff < 60) {
    return '刚刚'
  }
  if (diff < 3600) {
    return `${Math.floor(diff / 60)}分钟前`
  }
  if (diff < 86400) {
    return `${Math.floor(diff / 3600)}小时前`
  }
  return `${Math.floor(diff / 86400)}天前`
}

/**
 * 手机号脱敏
 */
export function maskPhone(phone: string): string {
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

/**
 * 金额数字转大写
 */
export function amountToChinese(num: number): string {
  const digits = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖']
  const units = ['', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿']
  const decimalUnits = ['角', '分']
  
  let integerPart = Math.floor(num)
  let decimalPart = Math.round((num - integerPart) * 100)
  
  let result = ''
  
  // 处理整数部分
  let unitIndex = 0
  let str = ''
  while (integerPart > 0) {
    const digit = integerPart % 10
    if (digit !== 0) {
      str = digits[digit] + units[unitIndex] + str
    } else if (str && !str.startsWith(digits[0])) {
      str = digits[0] + str
    }
    integerPart = Math.floor(integerPart / 10)
    unitIndex++
  }
  result = str || digits[0]
  
  // 处理小数部分
  if (decimalPart > 0) {
    const decimalStr = (decimalPart / 10).toFixed(0)
    if (Math.floor(decimalPart / 10) > 0) {
      result += digits[Math.floor(decimalPart / 10)] + decimalUnits[0]
    }
    if (decimalPart % 10 > 0) {
      result += digits[decimalPart % 10] + decimalUnits[1]
    }
  }
  
  return result
}

/**
 * 延迟函数
 */
export function sleep(ms: number): Promise<void> {
  return new Promise(resolve => setTimeout(resolve, ms))
}

/**
 * 生成订单号
 */
export function generateOrderNo(): string {
  const date = new Date()
  const yyyy = date.getFullYear()
  const MM = (date.getMonth() + 1).toString().padStart(2, '0')
  const dd = date.getDate().toString().padStart(2, '0')
  const HH = date.getHours().toString().padStart(2, '0')
  const mm = date.getMinutes().toString().padStart(2, '0')
  const ss = date.getSeconds().toString().padStart(2, '0')
  const random = Math.floor(Math.random() * 10000).toString().padStart(4, '0')
  
  return `${yyyy}${MM}${dd}${HH}${mm}${ss}${random}`
}
