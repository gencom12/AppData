import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
from sklearn import datasets, linear_model
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.model_selection import train_test_split

# Load the dataset
df = pd.read_csv("avocado_cleaned.csv")

# Remove non-numeric columns if present
df_numeric = df.select_dtypes(include=[np.number])

# Split the dataset into independent and dependent variables
X = df_numeric.drop(columns=["AveragePrice"])
y = df_numeric["AveragePrice"]

# Display scatterplot for each independent variable against the dependent variable
for col in X.columns:
    plt.figure(figsize=(8, 6))
    sns.scatterplot(x=X[col], y=y)
    plt.title(f"Scatterplot of {col} vs AveragePrice")
    plt.xlabel(col)
    plt.ylabel("AveragePrice")
    plt.show()

# Calculate correlation matrix
correlation_matrix = df_numeric.corr()
print("Correlation Matrix:")
print(correlation_matrix)

# Fit a linear model using sklearn
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
regr = linear_model.LinearRegression()
regr.fit(X_train, y_train)

# Assess relationship strength
y_pred = regr.predict(X_test)
print("Mean squared error: %.2f" % mean_squared_error(y_test, y_pred))
print("Coefficient of determination: %.2f" % r2_score(y_test, y_pred))

# Select variables with correlation greater than 0.5 in absolute value
high_correlation_vars = correlation_matrix[abs(correlation_matrix["AveragePrice"]) > 0.5].index.tolist()
print("Variables chosen for prediction:", high_correlation_vars)

# Fit a linear model using the chosen variables
X_chosen = df_numeric[high_correlation_vars]
X_train_chosen, X_test_chosen, y_train_chosen, y_test_chosen = train_test_split(X_chosen, y, test_size=0.2, random_state=42)
regr_chosen = linear_model.LinearRegression()
regr_chosen.fit(X_train_chosen, y_train_chosen)

# Assess relationship strength for chosen variables
y_pred_chosen = regr_chosen.predict(X_test_chosen)
print("Mean squared error (chosen variables): %.2f" % mean_squared_error(y_test_chosen, y_pred_chosen))
print("Coefficient of determination (chosen variables): %.2f" % r2_score(y_test_chosen, y_pred_chosen))