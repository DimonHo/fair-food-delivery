import Taro from '@tarojs/taro'
import { useState, useEffect } from 'react'
import { View, Image, Text, ScrollView } from '@tarojs/components'
import './index.scss'
import { useAppStore } from '../../store'
import { Product, Merchant } from '../../../../shared/types'

export default function MerchantDetail() {
  const { addToCart, cart } = useAppStore()
  const [merchant, setMerchant] = useState<Merchant | null>(null)
  const [products, setProducts] = useState<Product[]>([])
  const [activeCategory, setActiveCategory] = useState(0)
  const [selectedSpec, setSelectedSpec] = useState<Product | null>(null)
  const [showSpecModal, setShowSpecModal] = useState(false)

  const id = Taro.getCurrentInstance().router?.params.id

  useEffect(() => {
    // 模拟获取商家信息
    setMerchant({
      id: Number(id) || 1,
      name: '好滋味餐厅',
      logo: 'https://via.placeholder.com/100',
      description: '主营快餐，品质保证',
      rating: 4.8,
      monthSales: 5000,
      deliveryTime: 30,
      distance: 1.2,
      minPrice: 20,
      deliveryFee: 3,
      address: '北京市朝阳区xxx路100号',
      status: 'open',
      tags: ['快餐', '营养', '优惠']
    })

    // 模拟获取商品列表
    setProducts([
      { id: 1, merchantId: 1, name: '招牌红烧肉', description: '肥而不腻', price: 28, image: '', categoryId: 1, sales: 100, stock: 50, status: 'on' },
      { id: 2, merchantId: 1, name: '宫保鸡丁', description: '辣味适中', price: 22, image: '', categoryId: 1, sales: 200, stock: 30, status: 'on' },
      { id: 3, merchantId: 1, name: '清炒时蔬', description: '新鲜蔬菜', price: 12, image: '', categoryId: 2, sales: 150, stock: 20, status: 'on' },
      { id: 4, merchantId: 1, name: '米饭', description: '香糯可口', price: 3, image: '', categoryId: 2, sales: 300, stock: 100, status: 'on' },
      { id: 5, merchantId: 1, name: '可乐', description: '冰镇饮料', price: 5, image: '', categoryId: 3, sales: 250, stock: 50, status: 'on' }
    ])
  }, [id])

  const categories = ['热销', '主食', '饮料']
  const getCartQuantity = (productId: number) => {
    const merchantCart = cart.find(c => c.merchantId === merchant?.id)
    const item = merchantCart?.items.find(i => i.productId === productId)
    return item?.quantity || 0
  }

  const handleAddToCart = (product: Product) => {
    addToCart(merchant!.id, {
      productId: product.id,
      name: product.name,
      price: product.price,
      quantity: 1
    })
    Taro.showToast({ title: '已加入购物车', icon: 'success' })
  }

  const getTotalQuantity = () => {
    const merchantCart = cart.find(c => c.merchantId === merchant?.id)
    return merchantCart?.items.reduce((sum, item) => sum + item.quantity, 0) || 0
  }

  const goToCart = () => {
    Taro.switchTab({ url: '/pages/cart/index' })
  }

  if (!merchant) return <View className='loading'>加载中...</View>

  return (
    <View className='merchant-page'>
      {/* 商家信息头部 */}
      <View className='merchant-header'>
        <Image className='merchant-logo' src={merchant.logo} mode='aspectFill' />
        <View className='merchant-info'>
          <Text className='merchant-name'>{merchant.name}</Text>
          <View className='merchant-tags'>
            {merchant.tags.map(tag => (
              <Text key={tag} className='tag'>{tag}</Text>
            ))}
          </View>
          <View className='merchant-stats'>
            <Text>评分 {merchant.rating}</Text>
            <Text>月售 {merchant.monthSales}</Text>
            <Text>约 {merchant.deliveryTime}分钟</Text>
          </View>
        </View>
      </View>

      {/* 商品分类和列表 */}
      <View className='merchant-body'>
        <ScrollView className='category-list' scrollY>
          {categories.map((cat, index) => (
            <View 
              key={cat} 
              className={`category-item ${activeCategory === index ? 'active' : ''}`}
              onClick={() => setActiveCategory(index)}
            >
              {cat}
            </View>
          ))}
        </ScrollView>

        <ScrollView className='product-list' scrollY>
          <View className='category-title'>{categories[activeCategory]}</View>
          {products.filter((_, i) => i % 3 === activeCategory).map(product => (
            <View key={product.id} className='product-item'>
              <Image className='product-image' src={product.image} mode='aspectFill' />
              <View className='product-info'>
                <Text className='product-name'>{product.name}</Text>
                <Text className='product-desc'>{product.description}</Text>
                <View className='product-bottom'>
                  <Text className='product-price'>¥{product.price}</Text>
                  <View className='add-btn'>
                    {getCartQuantity(product.id) > 0 ? (
                      <View className='quantity-control'>
                        <Text>-</Text>
                        <Text>{getCartQuantity(product.id)}</Text>
                        <Text>+</Text>
                      </View>
                    ) : (
                      <Text onClick={() => handleAddToCart(product)}>+</Text>
                    )}
                  </View>
                </View>
              </View>
            </View>
          ))}
        </ScrollView>
      </View>

      {/* 购物车底部栏 */}
      {getTotalQuantity() > 0 && (
        <View className='cart-bar' onClick={goToCart}>
          <View className='cart-icon'>
            <Text>🛒</Text>
            <View className='badge'>{getTotalQuantity()}</View>
          </View>
          <Text className='cart-total'>¥{cart.find(c => c.merchantId === merchant.id)?.items.reduce((sum, i) => sum + i.price * i.quantity, 0) || 0}</Text>
          <View className='checkout-btn'>去结算</View>
        </View>
      )}
    </View>
  )
}
