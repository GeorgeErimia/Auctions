export const convertTimestampToDate = (timestamp) => {
  var dateResult = new Date(timestamp);
  //   console.log(dateResult);
  return dateResult;
};

export const convertToDateFormat = (timestamp) => {
  var dateResult = convertTimestampToDate(timestamp);
  return (
    dateResult.getDate() +
    "." +
    (dateResult.getMonth() + 1) +
    "." +
    dateResult.getFullYear()
  );
};

export const convertToHours = (timestamp) => {
  var dateResult = convertTimestampToDate(timestamp);
  return dateResult.getHours() + ":" + dateResult.getMinutes();
};

export const convertToDisplay = (timestamp) => {
  var dateResult = new Date(timestamp);
  var currentDate = new Date();

  if (dateResult < currentDate) return "Ended";

  var yearDiff = dateResult.getFullYear() - currentDate.getFullYear();
  var monthDiff = dateResult.getMonth() - currentDate.getMonth();
  var dayDiff = dateResult.getDate() - currentDate.getDate();
  var hourDiff = dateResult.getHours() - currentDate.getHours();
  var minuteDiff = dateResult.getMinutes() - currentDate.getMinutes();
  var secondDiff = dateResult.getSeconds() - currentDate.getSeconds();

  // Adjust for negative differences
  if (secondDiff < 0) {
    minuteDiff--;
    secondDiff += 60;
  }
  if (minuteDiff < 0) {
    hourDiff--;
    minuteDiff += 60;
  }
  if (hourDiff < 0) {
    dayDiff--;
    hourDiff += 24;
  }
  if (dayDiff < 0) {
    monthDiff--;
    var tempDate = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      0
    );
    dayDiff += tempDate.getDate();
  }
  if (monthDiff < 0) {
    yearDiff--;
    monthDiff += 12;
  }

  // Construct the result string
  var result = "";
  if (yearDiff > 0) {
    result += yearDiff + " Years";
    if (monthDiff > 0 || dayDiff > 0 || hourDiff > 0 || minuteDiff > 0)
      result += ", ";
  }
  if (monthDiff > 0) {
    result += monthDiff + " Months";
    if (dayDiff > 0 || hourDiff > 0 || minuteDiff > 0) result += ", ";
  }
  if (dayDiff > 0) {
    result += dayDiff + " Days";
    if (hourDiff > 0 || minuteDiff > 0) result += ", ";
  }
  if (hourDiff > 0) {
    result += hourDiff + " Hours";
    if (minuteDiff > 0) result += ", ";
  }
  if (minuteDiff > 0) {
    result += minuteDiff + " Minutes";
    if (hourDiff === 0 && secondDiff > 0) result += ", ";
  }
  if (secondDiff > 0 && hourDiff === 0) {
    result += secondDiff + " Seconds";
  }

  return result;
};
