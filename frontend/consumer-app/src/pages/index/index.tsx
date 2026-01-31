import React, { useEffect, useState } from 'react'
import Taro from '@tarojs/taro'
import { View, Text, Image, ScrollView, Input } from '@tarojs/components'
import { useAppStore } from '../../store'
import './index.scss'

interface Merchant {
  id: number
  name: string
  logo: string
  rating: number
  monthSales: number
  deliveryTime: number
  distance: number
  minPrice: number
  tags: string[]
}

export default function Index () {
  const { location, setLocation } = useAppStore()
  const [merchants, setMerchants] = useState<Merchant[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    // 获取位置
    getLocation()
    // 加载商家列表
    fetchMerchants()
  }, [])

  const getLocation = () => {
    Taro.getLocation({
      type: 'gcj02',
      success: (res) => {
        setLocation({
          latitude: res.latitude,
          longitude: res.longitude,
          address: '正在获取地址...'
        })
        // 逆地理编码
        Taro.request({
          url: `https://apis.map.qq.com/ws/geocoder/v1/?location=${res.latitude},${res.longitude}&key=YOUR_KEY`,
          success: (res) => {
            if (res.data.status === 0) {
              setLocation({
                latitude: res.latitude,
                longitude: res.longitude,
                address: res.data.result.address
              })
            }
          }
        })
      },
      fail: () => {
        // 默认地址
        setLocation({
          latitude: 39.9042,
          longitude: 116.4074,
          address: '北京市'
        })
      }
    })
  }

  const fetchMerchants = async () => {
    setLoading(true)
    try {
      // TODO: 调用真实 API
      // const res = await Taro.request({ url: '/api/merchant/list' })
      
      // 模拟数据
      await new Promise(resolve => setTimeout(resolve, 500))
      setMerchants([
        {
          id: 1,
          name: '招牌牛肉面',
          logo: 'https://via.placeholder.com/80',
          rating: 4.8,
          monthSales: 5000,
          deliveryTime: 25,
          distance: 1.2,
          minPrice: 15,
          tags: ['月售5000+', '速度快', '招牌推荐']
        },
        {
          id: 2,
          name: '川香小炒',
          logo: 'https://via.placeholder.com/80',
          rating: 4.6,
          monthSales: 3200,
          deliveryTime: 30,
          distance: 2.5,
          minPrice: 20,
          tags: ['月售3000+', '川菜']
        },
        {
          id: 3,
          name: '快餐便当',
          logo: 'https://via.placeholder.com/80',
          rating: 4.5,
          monthSales: 8000,
          deliveryTime: 20,
          distance: 0.8,
          minPrice: 12,
          tags: ['月售8000+', '性价比高']
        }
      ])
    } finally {
      setLoading(false)
    }
  }

  const goToMerchant = (id: number) => {
    Taro.navigateTo({
      url: `/pages/merchant/index?id=${id}`
    })
  }

  return (
    <View className='index-page'>
      {/* 搜索栏 */}
      <View className='search-bar'>
        <View className='location' onClick={() => Taro.navigateTo({ url: '/pages/location/index' })}>
          <Text className='icon'>📍</Text>
          <Text className='address'>{location?.address || '定位中...'}</Text>
          <Text className='arrow'>▼</Text>
        </View>
        <View className='search-input' onClick={() => Taro.navigateTo({ url: '/pages/search/index' })}>
          <Text className='icon'>🔍</Text>
          <Text className='placeholder'>搜索商家和商品</Text>
        </View>
      </View>

      {/* 分类导航 */}
      <ScrollView className='category-nav' scrollX>
        {['全部', '快餐便当', '特色菜', '小吃夜宵', '甜品饮品', '早餐'].map((cat, idx) => (
          <View key={idx} className={`category-item ${idx === 0 ? 'active' : ''}`}>
            {cat}
          </View>
        ))}
      </ScrollView>

      {/* 商家列表 */}
      <ScrollView className='merchant-list' scrollY>
        <View className='list-header'>
          <Text className='title'>附近商家</Text>
          <Text className='subtitle'>{merchants.length} 家</Text>
        </View>

        {loading ? (
          <View className='loading'>加载中...</View>
        ) : (
          merchants.map(merchant => (
            <View key={merchant.id} className='merchant-card' onClick={() => goToMerchant(merchant.id)}>
              <Image className='logo' src={merchant.logo} mode='aspectFill' />
              <View className='info'>
                <View className='name-row'>
                  <Text className='name'>{merchant.name}</Text>
                  <View className='tags'>
                    {merchant.tags.slice(0, 2).map((tag, i) => (
                      <Text key={i} className='tag'>{tag}</Text>
                    ))}
                  </View>
                </View>
                <View className='rating-row'>
                  <Text className='rating'>⭐ {merchant.rating}</Text>
                  <Text className='sales'>月售{merchant.monthSales}</Text>
                  <Text className='time'>{merchant.deliveryTime}分钟</Text>
                  <Text className='distance'>{merchant.distance}km</Text>
                </View>
                <View className='price-row'>
                  <Text className='min-price'>起送 ¥{merchant.minPrice}</Text>
                </View>
              </View>
            </View>
          ))
        )}
      </ScrollView>
    </View>
  )
}
