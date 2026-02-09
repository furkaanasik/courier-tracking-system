#!/bin/bash

# Color Definitions
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
BASE_URL="http://localhost:8080/api/v1/courier"
COURIER_ID="CRR-123"

echo -e "${BLUE}==============================================${NC}"
echo -e "${BLUE}   Courier Tracking System - Simulation      ${NC}"
echo -e "${BLUE}==============================================${NC}\n"

# Function to send location data
send_location() {
    local lat=$1
    local lon=$2
    local note=$3
    local ts=$(date -u +"%Y-%m-%dT%H:%M:%S")

    echo -e "${YELLOW}📍 Sending Location: ${note}${NC}"
    echo -e "   Lat: $lat, Lon: $lon"

    curl -s -X POST "$BASE_URL/location" \
      -H "Content-Type: application/json" \
      -d "{
        \"courierId\": \"$COURIER_ID\",
        \"latitude\": $lat,
        \"longitude\": $lon,
        \"timestamp\": \"$ts\"
      }"
    echo -e "\n"
}

# Step 1: Near Ataşehir MMM Migros
send_location 40.9923307 29.1244229 "Ataşehir MMM Migros"
sleep 1

# Step 2: Near Novada MMM Migros (Distance covered)
send_location 40.986106 29.1161293 "Novada MMM Migros"
sleep 1

# Step 3: Back to Ataşehir (Testing re-entry logic)
send_location 40.9924 29.1245 "Back to Ataşehir (Re-entry Test)"
sleep 1

echo -e "${BLUE}----------------------------------------------${NC}"

# Query Total Distance
echo -e "${GREEN}📊 Querying Total Distance for $COURIER_ID...${NC}"
curl -s "$BASE_URL/$COURIER_ID/distance" | jq . 2>/dev/null || curl -s "$BASE_URL/$COURIER_ID/distance"
echo -e "\n"

# Query Store Entry Logs
echo -e "${GREEN}🏪 Querying Store Entries for $COURIER_ID...${NC}"
curl -s "$BASE_URL/$COURIER_ID/store-entries" | jq . 2>/dev/null || curl -s "$BASE_URL/$COURIER_ID/store-entries"
echo -e "\n"

echo -e "${BLUE}==============================================${NC}"
echo -e "${GREEN}✅ Simulation Finished Successfully!${NC}"
echo -e "${BLUE}==============================================${NC}"