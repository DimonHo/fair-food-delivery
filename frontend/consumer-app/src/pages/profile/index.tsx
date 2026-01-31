import Taro from '@tarojs/taro'
import { useEffect, useState } from 'react'
import { View, Text, Image, Button } from '@tarojs/components'
import './index.scss'
import { useAppStore } from '../../store'

export default function ProfilePage() {
  const { userInfo, token, logout } = useAppStore()
  const [stats, setStats] = useState({ orders: 0, points: 0, coupons: 0 })

  useEffect(() => {
    if (userInfo) {
      setStats({ orders: 12, points: 580, coupons: 3 })
    }
  }, [userInfo])

  const handleLogout = () => {
    Taro.showModal({
      title: '退出登录',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          logout()
          Taro.showToast({ title: '已退出', icon: 'success' })
        }
      }
    })
  }

  const goToLogin = () => {
    Taro.navigateTo({ url: '/pages/login/index' })
  }

  const goToAddress = () => {
    Taro.navigateTo({ url: '/pages/address/index' })
  }

  const goToOrderList = (status?: string) => {
    Taro.navigateTo({ url: `/pages/order/list${status ? `?status=${status}` : ''}` })
  }

  return (
    <View className='profile-page'>
      {/* 用户信息头部 */}
      <View className='profile-header'>
        {userInfo ? (
          <>
            <Image 
              className='avatar' 
              src={userInfo.avatar || 'https://via.placeholder.com/80'} 
              mode='aspectFill' 
            />
            <View className='user-info'>
              <Text className='nickname'>{userInfo.nickname}</Text>
              <Text className='phone'>{userInfo.phone}</Text>
            </View>
          </>
        ) : (
          <View className='login-tip' onClick={goToLogin}>
            <Text>点击登录，享受更多服务</Text>
          </View>
        )}
      </View>

      {/* 统计卡片 */}
      <View className='stats-card'>
        <View className='stat-item' onClick={() => goToOrderList()}>
          <Text className='stat-value'>{stats.orders}</Text>
          <Text className='stat-label'>订单</Text>
        </View>
        <View className='stat-item'>
          <Text className='stat-value'>{stats.points}</Text>
          <Text className='stat-label'>积分</Text>
        </View>
        <View className='stat-item'>
          <Text className='stat-value'>{stats.coupons}</Text>
          <Text className='stat-label'>优惠券</Text>
        </View>
      </View>

      {/* 订单状态 */}
      <View className='section order-section'>
        <View className='section-header' onClick={() => goToOrderList()}>
          <Text className='section-title'>我的订单</Text>
          <Text className='section-more'>全部订单 ›</Text>
        </View>
        <View className='order-status-list'>
          <View className='status-item' onClick={() => goToOrderList('pending')}>
            <Text className='status-icon'>💰</Text>
            <Text className='status-text'>待付款</Text>
          </View>
          <View className='status-item' onClick={() => goToOrderList('cooking')}>
            <Text className='status-icon'>👨‍🍳</Text>
            <Text className='status-text'>制作中</Text>
          </View>
          <View className='status-item' onClick={() => goToOrderList('delivering')}>
            <Text className='status-icon'>🚴</Text>
            <Text className='status-text'>配送中</Text>
          </View>
          <View className='status-item' onClick={() => goToOrderList('completed')}>
            <Text className='status-icon'>✅</Text>
            <Text className='status-text'>已完成</Text>
          </View>
        </View>
      </View>

      {/* 功能列表 */}
      <View className='section menu-section'>
        <View className='menu-item' onClick={goToAddress}>
          <Text className='menu-icon'>📍</Text>
          <Text className='menu-text'>收货地址</Text>
          <Text className='menu-arrow'>›</Text>
        </View>
        <View className='menu-item'>
          <Text className='menu-icon'>💰</Text>
          <Text className='menu-text'>我的钱包</Text>
          <Text className='menu-arrow'>›</Text>
        </View>
        <View className='menu-item'>
          <Text className='menu-icon'>🎫</Text>
          <Text className='menu-text'>优惠券</Text>
          <Text className='menu-arrow'>›</Text>
        </View>
        <View className='menu-item'>
          <Text className='menu-icon'>❓</Text>
          <Text className='menu-text'>帮助中心</Text>
          <Text className='menu-arrow'>›</Text>
        </View>
        <View className='menu-item'>
          <Text className='menu-icon'>⚙️</Text>
          <Text className='menu-text'>设置</Text>
          <Text className='menu-arrow'>›</Text>
        </View>
      </View>

      {userInfo && (
        <Button className='logout-btn' onClick={handleLogout}>
          退出登录
        </Button>
      )}
    </View>
  )
}
