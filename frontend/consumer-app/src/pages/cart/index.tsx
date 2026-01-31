import React, { useEffect, useState } from 'react'
import Taro from '@tarojs/taro'
import { View, Text, Image, Button } from '@tarojs/components'
import { useAppStore } from '../../store'
import './index.scss'

interface CartItem {
  productId: number
  name: string
  price: number
  quantity: number
  spec?: string
}

interface CartMerchant {
  merchantId: number
  merchantName: string
  items: CartItem[]
}

export default function Cart () {
  const { cart, setToken, removeFromCart, clearCart } = useAppStore()
  const [merchantCarts, setMerchantCarts] = useState<CartMerchant[]>([])
  const [totalPrice, setTotalPrice] = useState(0)

  useEffect(() => {
    // 模拟获取商家信息
    const mockMerchantCarts: CartMerchant[] = cart.map(c => ({
      merchantId: c.merchantId,
      merchantName: `商家${c.merchantId}`,
      items: c.items
    }))
    setMerchantCarts(mockMerchantCarts)
    
    // 计算总价
    let total = 0
    cart.forEach(m => {
      m.items.forEach(item => {
        total += item.price * item.quantity
      })
    })
    setTotalPrice(total)
  }, [cart])

  const handleCheckout = () => {
    if (!cart.length) {
      Taro.showToast({ title: '购物车为空', icon: 'none' })
      return
    }
    
    // 跳转到下单页面
    Taro.navigateTo({
      url: '/pages/order/checkout'
    })
  }

  const updateQuantity = (merchantId: number, productId: number, delta: number) => {
    // 这里需要完善 store 的方法
    Taro.showToast({ title: '功能开发中', icon: 'none' })
  }

  return (
    <View className='cart-page'>
      <View className='header'>
        <Text className='title'>购物车</Text>
        {cart.length > 0 && (
          <Text className='clear-btn' onClick={() => clearCart(cart[0]?.merchantId || 0)}>
            清空
          </Text>
        )}
      </View>

      {merchantCarts.length === 0 ? (
        <View className='empty-cart'>
          <Text className='icon'>🛒</Text>
          <Text className='text'>购物车空空如也</Text>
          <Button className='go-shop' onClick={() => Taro.switchTab({ url: '/pages/index/index' })}>
            去逛逛
          </Button>
        </View>
      ) : (
        <>
          {merchantCarts.map(merchant => (
            <View key={merchant.merchantId} className='merchant-section'>
              <View className='merchant-header'>
                <Text className='name'>{merchant.merchantName}</Text>
              </View>
              
              {merchant.items.map(item => (
                <View key={item.productId} className='cart-item'>
                  <View className='item-info'>
                    <Text className='name'>{item.name}</Text>
                    {item.spec && <Text className='spec'>{item.spec}</Text>}
                    <Text className='price'>¥{item.price}</Text>
                  </View>
                  <View className='quantity-control'>
                    <Text 
                      className='btn minus'
                      onClick={() => updateQuantity(merchant.merchantId, item.productId, -1)}
                    >
                      -
                    </Text>
                    <Text className='num'>{item.quantity}</Text>
                    <Text 
                      className='btn plus'
                      onClick={() => updateQuantity(merchant.merchantId, item.productId, 1)}
                    >
                      +
                    </Text>
                  </View>
                </View>
              ))}
            </View>
          ))}

          {/* 底部结算栏 */}
          <View className='checkout-bar'>
            <View className='total'>
              <Text className='label'>合计：</Text>
              <Text className='price'>¥{totalPrice.toFixed(2)}</Text>
            </View>
            <Button className='checkout-btn' onClick={handleCheckout}>
              去结算 ({cart.reduce((acc, m) => acc + m.items.length, 0)})
            </Button>
          </View>
        </>
      )}
    </View>
  )
}
