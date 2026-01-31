import Taro from '@tarojs/taro'
import { useState } from 'react'
import { View, Text, Input, Image } from '@tarojs/components'
import './index.scss'
import { Merchant } from '../../../../shared/types'

export default function SearchPage() {
  const [keyword, setKeyword] = useState('')
  const [history, setHistory] = useState(['汉堡', '披萨', '奶茶'])
  const [results, setResults] = useState<Merchant[]>([])
  const [hasSearched, setHasSearched] = useState(false)

  const doSearch = () => {
    if (!keyword.trim()) return
    
    setHasSearched(true)
    // 模拟搜索结果
    setResults([
      {
        id: 1,
        name: `搜索"${keyword}"相关商家`,
        logo: '',
        description: '附近热门商家',
        rating: 4.8,
        monthSales: 5000,
        deliveryTime: 30,
        distance: 1.2,
        minPrice: 20,
        deliveryFee: 3,
        address: '',
        status: 'open',
        tags: []
      }
    ])
    
    // 添加到历史记录
    if (!history.includes(keyword)) {
      setHistory([keyword, ...history].slice(0, 10))
    }
  }

  const clearHistory = () => {
    setHistory([])
  }

  const searchByKeyword = (kw: string) => {
    setKeyword(kw)
    doSearch()
  }

  const goToMerchant = (id: number) => {
    Taro.navigateTo({ url: `/pages/merchant/index?id=${id}` })
  }

  return (
    <View className='search-page'>
      <View className='search-header'>
        <View className='search-bar'>
          <Text className='search-icon'>🔍</Text>
          <Input 
            className='search-input'
            placeholder='搜索商家和美食'
            value={keyword}
            onInput={(e) => setKeyword(e.detail.value)}
            onConfirm={doSearch}
            focus
          />
        </View>
        <Text className='cancel-btn' onClick={() => Taro.navigateBack()}>取消</Text>
      </View>

      {!hasSearched ? (
        <View className='history-section'>
          <View className='section-header'>
            <Text className='title'>搜索历史</Text>
            <Text className='clear-btn' onClick={clearHistory}>清空</Text>
          </View>
          <View className='history-list'>
            {history.map((kw, index) => (
              <Text 
                key={index} 
                className='history-item'
                onClick={() => searchByKeyword(kw)}
              >
                {kw}
              </Text>
            ))}
          </View>
        </View>
      ) : (
        <View className='results-section'>
          {results.length > 0 ? (
            results.map(merchant => (
              <View 
                key={merchant.id} 
                className='result-item'
                onClick={() => goToMerchant(merchant.id)}
              >
                <Image className='merchant-logo' src={merchant.logo} mode='aspectFill' />
                <View className='merchant-info'>
                  <Text className='merchant-name'>{merchant.name}</Text>
                  <Text className='merchant-desc'>{merchant.description}</Text>
                  <View className='merchant-stats'>
                    <Text>评分 {merchant.rating}</Text>
                    <Text>月售 {merchant.monthSales}</Text>
                  </View>
                </View>
              </View>
            ))
          ) : (
            <View className='no-result'>
              <Text>未找到相关商家</Text>
            </View>
          )}
        </View>
      )}
    </View>
  )
}
