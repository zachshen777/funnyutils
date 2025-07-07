-- rateLimiter.lua
local key = KEYS[1]
local limit = tonumber(ARGV[1])
local period = tonumber(ARGV[2])
local current = tonumber(redis.call('get', key) or "0")

if current + 1 > limit then
    return redis.call('pttl', key)
else
    local count = redis.call('incrby', key, 1)
    if count == 1 then
        redis.call('pexpire', key, period * 1000)
    end
    return 0
end