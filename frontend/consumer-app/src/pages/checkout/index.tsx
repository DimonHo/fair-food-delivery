import Taro from '@tarojs/taro'
import { useState, useEffect } from 'react'
import { View, Text, Image, Radio } from '@tarojs/components'
import './index.scss'
import { useAppStore } from '../../store'
import { Address, OrderItem } from '../../../../shared/types'

export default function CheckoutPage() {
  const { cart, token, userInfo } = useAppStore()
  const [address, setAddress] = useState<Address | null>(null)
  const [paymentMethod, setPaymentMethod] = useState('wechat')
  const [remark, setRemark] = useState('')

  useEffect(() => {
    // 模拟获取默认地址
    setAddress({
      id: 1,
      name: userInfo?.nickname || '用户',
      phone: userInfo?.phone || '138****8888',
      province: '北京市',
      city: '朝阳区',
      district: 'xxx街道',
      detail: 'xxx小区1号楼101',
      isDefault: true
    })
  }, [userInfo])

  const calculateTotal = () => {
    if (cart.length === 0) return { itemsTotal: 0, deliveryFee: 0, total: 0 }
    
    const itemsTotal = cart[0]?.items.reduce((sum, item) => sum + item.price * item.quantity, 0) || 0
    const deliveryFee = 3
    return { itemsTotal, deliveryFee, total: itemsTotal + deliveryFee }
  }

  const { itemsTotal, deliveryFee, total } = calculateTotal()

  const submitOrder = () => {
    if (!address) {
      Taro.showToast({ title: '请选择收货地址', icon: 'none' })
      return
    }
    if (cart.length === 0) {
      Taro.showToast({ title: '购物车为空', icon: 'none' })
      return
    }
    
    // 模拟下单
    Taro.showLoading({ title: '下单中...' })
    setTimeout(() => {
      Taro.hideLoading()
      Taro.showToast({ title: '下单成功', icon: 'success' })
      Taro.redirectTo({ url: '/pages/order/list' })
    }, 1000)
  }

  const goToAddress = () => {
    Taro.navigateTo({ url: '/pages/address/index' })
  }

  if (!token) {
    return (
      <View className='checkout-page'>
        <View className='login-tip'>请先登录后下单</View>
      </View>
    )
  }

  return (
    <View className='checkout-page'>
      {/* 地址选择 */}
      <View className='section address-section' onClick={goToAddress}>
        {address ? (
          <>
            <View className='address-info'>
              <View className='address-user'>
                <Text className='name'>{address.name}</Text>
                <Text className='phone'>{address.phone}</Text>
                {address.isDefault && <Text className='tag'>默认</Text>}
              </View>
              <Text className='address-detail'>
                {address.province}{address.city}{address.district}{address.detail}
              </Text>
            </View>
            <Text className='arrow'>›</Text>
          </>
        ) : (
          <View className='add-address'>
            <Text>+ 添加收货地址</Text>
          </View>
        )}
      </View>

      {/* 商品列表 */}
      <View className='section'>
        <View className='section-title'>商品信息</View>
        {cart[0]?.items.map((item, index) => (
          <View key={index} className='cart-item'>
            <Text className='item-name'>{item.name}</Text>
            <Text className='item-qty'>x{item.quantity}</Text>
            <Text className='item-price'>¥{item.price * item.quantity}</Text>
          </View>
        ))}
      </View>

      {/* 支付方式 */}
      <View className='section'>
        <View className='section-title'>支付方式</View>
        <View className='payment-option' onClick={() => setPaymentMethod('wechat')}>
          <Text>微信支付</Text>
          <Radio checked={paymentMethod === 'wechat'} color='#ff4d4f' />
        </View>
        <View className='payment-option' onClick={() => setPaymentMethod('alipay')}>
          <Text>支付宝支付</Text>
          <Radio checked={paymentMethod === 'alipay'} color='#ff4d4f' />
        </View>
      </View>

      {/* 备注 */}
      <View className='section'>
        <View className='section-title'>订单备注</View>
        <Input 
          className='remark-input'
          placeholder='请输入备注（如：不要辣）'
          value={remark}
          onInput={(e) => setRemark(e.detail.value)}
        />
      </View>

      {/* 底部结算栏 */}
      <View className='checkout-bar'>
        <View className='price-info'>
          <Text className='total-price'>¥{total.toFixed(2)}</Text>
          <Text className='fee'>含配送费¥{deliveryFee}</Text>
        </View>
        <View className='submit-btn' onClick={submitOrder}>
          提交订单
        </View>
      </View>
    </View>
  )
}
