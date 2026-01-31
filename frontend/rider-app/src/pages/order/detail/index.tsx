import Taro from '@tarojs/taro'
import { useState, useEffect } from 'react'
import { View, Text, Button } from '@tarojs/components'
import './index.scss'

interface TaskOrder {
  id: number
  orderNo: string
  status: 'picked' | 'delivering' | 'completed'
  merchantName: string
  merchantAddress: string
  merchantPhone: string
  deliveryName: string
  deliveryPhone: string
  deliveryAddress: string
  earning: number
  items: string[]
}

export default function RiderOrderDetailPage() {
  const [order, setOrder] = useState<TaskOrder | null>(null)
  const id = Taro.getCurrentInstance().router?.params.id

  useEffect(() => {
    // 模拟订单详情
    setOrder({
      id: Number(id) || 1,
      orderNo: 'FF20240131001',
      status: 'picked',
      merchantName: '好滋味餐厅',
      merchantAddress: '朝阳区xxx路100号',
      merchantPhone: '010-12345678',
      deliveryName: '张三',
      deliveryPhone: '138****8888',
      deliveryAddress: '海淀区xxx小区1号楼101',
      earning: 8.5,
      items: ['招牌红烧肉 x1', '宫保鸡丁 x1', '米饭 x1']
    })
  }, [id])

  const confirmPickup = () => {
    Taro.showLoading({ title: '确认中...' })
    setTimeout(() => {
      Taro.hideLoading()
      setOrder(o => o ? { ...o, status: 'delivering' } : null)
      Taro.showToast({ title: '已取餐', icon: 'success' })
    }, 1000)
  }

  const confirmDelivery = () => {
    Taro.showModal({
      title: '确认送达',
      content: '是否确认已将餐品送达？',
      success: (res) => {
        if (res.confirm) {
          Taro.showLoading({ title: '提交中...' })
          setTimeout(() => {
            Taro.hideLoading()
            Taro.showToast({ title: '配送完成', icon: 'success' })
            setTimeout(() => Taro.navigateBack(), 1000)
          }, 1000)
        }
      }
    })
  }

  const callMerchant = () => {
    Taro.makePhoneCall({ phoneNumber: order?.merchantPhone || '' })
  }

  const callCustomer = () => {
    Taro.makePhoneCall({ phoneNumber: order?.deliveryPhone?.replace('*', '') || '' })
  }

  const navigateToMerchant = () => {
    Taro.openLocation({
      latitude: 39.9042,
      longitude: 116.4074,
      name: order?.merchantName,
      address: order?.merchantAddress
    })
  }

  const navigateToDelivery = () => {
    Taro.openLocation({
      latitude: 39.9142,
      longitude: 116.4174,
      name: order?.deliveryName,
      address: order?.deliveryAddress
    })
  }

  if (!order) return <View className='loading'>加载中...</View>

  return (
    <View className='rider-order-detail-page'>
      {/* 状态进度 */}
      <View className='status-progress'>
        <View className={`step ${order.status !== 'pending' ? 'active' : ''}`}>
          <View className='step-dot'>1</View>
          <Text className='step-text'>待取餐</Text>
        </View>
        <View className={`step-line ${order.status !== 'pending' ? 'active' : ''}`}></View>
        <View className={`step ${['delivering', 'completed'].includes(order.status) ? 'active' : ''}`}>
          <View className='step-dot'>2</View>
          <Text className='step-text'>配送中</Text>
        </View>
        <View className={`step-line ${order.status === 'completed' ? 'active' : ''}`}></View>
        <View className={`step ${order.status === 'completed' ? 'active' : ''}`}>
          <View className='step-dot'>3</View>
          <Text className='step-text'>已完成</Text>
        </View>
      </View>

      {/* 订单信息 */}
      <View className='order-info-card'>
        <View className='card-header'>
          <Text className='order-no'>{order.orderNo}</Text>
          <Text className='earning'>收入 ¥{order.earning}</Text>
        </View>

        <View className='route-section'>
          <View className='route-item pickup'>
            <View className='route-header'>
              <Text className='route-icon'>🏪</Text>
              <Text className='route-title'>取餐点</Text>
            </View>
            <Text className='route-name'>{order.merchantName}</Text>
            <Text className='route-addr'>{order.merchantAddress}</Text>
            <View className='route-actions'>
              <Button size='mini' onClick={navigateToMerchant}>导航</Button>
              <Button size='mini' onClick={callMerchant}>联系商家</Button>
            </View>
          </View>

          <View className='route-arrow'>↓</View>

          <View className='route-item delivery'>
            <View className='route-header'>
              <Text className='route-icon'>🏠</Text>
              <Text className='route-title'>送餐点</Text>
            </View>
            <Text className='route-name'>{order.deliveryName}</Text>
            <Text className='route-addr'>{order.deliveryAddress}</Text>
            <View className='route-actions'>
              <Button size='mini' onClick={navigateToDelivery}>导航</Button>
              <Button size='mini' onClick={callCustomer}>联系顾客</Button>
            </View>
          </View>
        </View>

        {/* 商品列表 */}
        <View className='items-section'>
          <Text className='section-title'>商品清单</Text>
          {order.items.map((item, index) => (
            <Text key={index} className='item-text'>{item}</Text>
          ))}
        </View>
      </View>

      {/* 底部操作 */}
      <View className='action-bar'>
        {order.status === 'picked' && (
          <Button className='action-btn primary' onClick={confirmPickup}>
            确认取餐
          </Button>
        )}
        {order.status === 'delivering' && (
          <Button className='action-btn primary' onClick={confirmDelivery}>
            确认送达
          </Button>
        )}
      </View>
    </View>
  )
}
